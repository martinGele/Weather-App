package com.martin.one.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.github.di.Injectable
import com.martin.one.R
import com.martin.weatherestonia.adapter.FourDaysFourcastAdapter
import com.martin.weatherestonia.model.Forecast
import com.martin.weatherestonia.model.FutureWeather
import com.martin.one.util.isConnected
import kotlinx.android.synthetic.main.fragment_main_screen.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MainScreenFragment : Fragment(), Injectable {


    private val adapterFutureWeather = FourDaysFourcastAdapter(arrayListOf())


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    val mainViewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main_screen, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (context!!.isConnected) {
            mainViewModel.showFourDaysWeather()
            mainViewModel.showCurrentWeather()

            mainViewModel.weather.observe(this, weatherListDataObserver)
            mainViewModel.loading.observe(this, loadingLiveDataObserver)
            mainViewModel.loadError.observe(this, onErrorLiveDataObserver)

        } else {

            Toast.makeText(context,R.string.no_internet, Toast.LENGTH_LONG).show()

            mainViewModel.getOfflineData().observe(this, weatherListDataObserverDB)
        }


        refresh_layout.setOnRefreshListener {
            if(context!!.isConnected) {
                weatherForecastRecycler.visibility = View.GONE
                listError.visibility = View.GONE
                loadingView.visibility = View.VISIBLE
                mainViewModel.showFourDaysWeather()
                mainViewModel.showCurrentWeather()

                refresh_layout.isRefreshing = false
            }else{
                refresh_layout.isRefreshing = false

                Toast.makeText(context,R.string.no_internet, Toast.LENGTH_LONG).show()

            }
        }


        weatherForecastRecycler.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterFutureWeather
        }


    }


    private val weatherListDataObserver = Observer<FutureWeather> { it ->
        it?.let {
            weatherForecastRecycler.visibility = View.VISIBLE
            adapterFutureWeather.updateWeatherList(it.forecasts as List<Forecast>)
        }


    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            listError.visibility = View.GONE
            weatherForecastRecycler.visibility = View.GONE
        }
    }
    private val onErrorLiveDataObserver = Observer<Boolean> { error ->
        listError.visibility = if (error) View.VISIBLE else View.GONE
    }

    private val weatherListDataObserverDB = Observer<FutureWeather> { it ->

        it?.let {
            weatherForecastRecycler.visibility = View.VISIBLE
            adapterFutureWeather.updateWeatherList(it.forecasts as List<Forecast>)
        }
    }

}
