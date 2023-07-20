package com.dev_musashi.onetouch.sensor

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor

class GyroSensor(
    context: Context
): AndroidSensor(
    context = context,
    sensorFeature = PackageManager.FEATURE_SENSOR_ACCELEROMETER,
    sensorType = Sensor.TYPE_ROTATION_VECTOR
)