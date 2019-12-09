package com.martin.one.api

import com.martin.weatherestonia.model.CurrentWeather
import com.martin.weatherestonia.model.FutureWeather
import io.reactivex.Single
import retrofit2.http.GET

interface WeatherApiService {

    @GET("forecast")
    fun getWeather(): Single<FutureWeather>

    @GET("current")
    fun getCurrentWeather(): Single<CurrentWeather>

}