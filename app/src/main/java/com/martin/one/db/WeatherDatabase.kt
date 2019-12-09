package com.martin.one.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.martin.one.db.WeatherDao
import com.martin.weatherestonia.model.CurrentWeather
import com.martin.weatherestonia.model.FutureWeather


@Database(entities = arrayOf(FutureWeather::class, CurrentWeather::class), version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao


}