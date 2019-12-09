package com.martin.one.ui.detail

import androidx.lifecycle.ViewModel
import com.martin.one.api.WeatherApiService
import com.martin.one.db.WeatherDao
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val weatherApiService: WeatherApiService, private val weatherDao: WeatherDao
) : ViewModel() {


}