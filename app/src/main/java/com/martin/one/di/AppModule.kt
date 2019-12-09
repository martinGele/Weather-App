/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.martin.one.di

import android.app.Application
import androidx.room.Room
import com.martin.one.api.WeatherApiService
import com.martin.one.db.WeatherDao
import com.martin.one.db.WeatherDatabase

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): WeatherApiService {
        return Retrofit.Builder()
            .baseUrl("https://weather.aw.ee/api/estonia/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): WeatherDatabase {
        return Room
            .databaseBuilder(app, WeatherDatabase::class.java, "weather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: WeatherDatabase): WeatherDao {
        return db.getWeatherDao()
    }

}
