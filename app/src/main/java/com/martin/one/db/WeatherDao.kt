package com.martin.one.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.martin.weatherestonia.model.CurrentWeather
import com.martin.weatherestonia.model.FutureWeather


@Dao
interface WeatherDao {


    @Query("SELECT * FROM day")
    fun getCurrentWeather(): LiveData<FutureWeather>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCurrent(currentWeather: FutureWeather)

    @Query("SELECT * FROM currentWeather")
    fun getCurrentObservation(): LiveData<CurrentWeather>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllObservation(currentWeather: CurrentWeather)
}