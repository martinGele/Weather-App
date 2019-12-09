package com.martin.one.ui.main


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.example.github.di.Injectable
import com.martin.one.R
import com.martin.one.binding.FragmentDataBindingComponent
import com.martin.one.databinding.FragmentMainScreenBinding
import com.martin.one.util.autoCleared
import com.martin.weatherestonia.model.CurrentWeather
import com.martin.weatherestonia.model.FutureWeather
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class MainScreenFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<FragmentMainScreenBinding>()


    val mainViewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen,container, false, dataBindingComponent)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.showCurrentWeather()
        mainViewModel.showFourDaysWeather()
        mainViewModel.weather.observe(this, weatherListDataObserver)
        mainViewModel.currentWeather.observe(this, currentWeatherDataObserver)
    }


    private val weatherListDataObserver = Observer<FutureWeather> { futureWeather ->
        Log.d("GetWeather", futureWeather.forecasts.toString())


    }
    private val currentWeatherDataObserver = Observer<CurrentWeather> { currentWeather ->
        Log.d("GetCurrent", currentWeather.observations.toString())
    }


}
