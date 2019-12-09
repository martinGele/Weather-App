package com.martin.one.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.example.github.di.Injectable
import com.martin.one.R
import com.martin.weatherestonia.model.CurrentWeather
import com.martin.weatherestonia.model.FutureWeather
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MainScreenFragment : Fragment(), Injectable {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainViewModel.showCurrentWeather()
        mainViewModel.showFourDaysWeather()
        mainViewModel.weather.observe(this, weatherListDataObserver)


        mainViewModel.currentWeather.observe(this, currentWeatherDataObserver)
    }


    private val weatherListDataObserver = Observer<FutureWeather> { futureWeather ->


    }
    private val currentWeatherDataObserver = Observer<CurrentWeather> { currentWeather ->


    }


}
