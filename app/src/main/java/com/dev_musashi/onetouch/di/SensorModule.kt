package com.dev_musashi.onetouch.di

import android.app.Application
import com.dev_musashi.onetouch.sensor.RotationSensor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Provides
    @Singleton
    fun provideListener(app: Application): RotationSensor {
        return RotationSensor(app)
    }

}