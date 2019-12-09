package com.martin.one.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.one.api.WeatherApiService
import com.martin.one.db.WeatherDao
import com.martin.weatherestonia.model.CurrentWeather
import com.martin.weatherestonia.model.FutureWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val weatherApiService: WeatherApiService, private val weatherDao: WeatherDao
) : ViewModel() {


    internal val weather by lazy { MutableLiveData<FutureWeather>() }
    internal val currentWeather by lazy { MutableLiveData<CurrentWeather>() }
    private val loadError by lazy { MutableLiveData<Boolean>() }
    private val loading by lazy { MutableLiveData<Boolean>() }


    private val disposable = CompositeDisposable()


    fun showFourDaysWeather() {
        loading.value = true
        getWeather()
    }

    fun showCurrentWeather() {
        loading.value = true
        getCurrentWeather()
    }

    private fun getWeather() {
        disposable.add(
            weatherApiService.getWeather()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<FutureWeather>() {
                    override fun onSuccess(futureWeather: FutureWeather) {


                        GlobalScope.launch(Dispatchers.IO) {
                            weatherDao.insertAllCurrent(futureWeather)
                        }
                        loadError.value = false
                        weather.value = futureWeather
                        loading.value = false

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                        weather.value = null
                    }
                })
        )

    }

    private fun getCurrentWeather() {

        disposable.add(

            weatherApiService.getCurrentWeather()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CurrentWeather>() {
                    override fun onSuccess(current: CurrentWeather) {


                        GlobalScope.launch(Dispatchers.IO) {
                            weatherDao.insertAllObservation(current)

                        }
                        loadError.value = false
                        currentWeather.value = current
                        loading.value = false

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                        currentWeather.value = null
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()

    }


}