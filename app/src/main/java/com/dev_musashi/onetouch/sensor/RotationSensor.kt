package com.dev_musashi.onetouch.sensor

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RotationSensor(
    context: Context
) {
    val degree = MutableStateFlow(0f)

    private val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    private val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    private val listener: SensorEventListener = object : SensorEventListener {
        private val rotationMatrix = FloatArray(9)
        private val orientationAngles = FloatArray(3)

        override fun onSensorChanged(event: SensorEvent) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)
            val pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()
            val roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()

            if (pitch >= -45 && pitch < 45 && roll >= 45) {
                degree.value = 270f
            }

            else if (pitch < -45 && roll >= -45 && roll < 45) {
                degree.value = 0f
            }

            else if (pitch >= -45 && pitch < 45 && roll < -45) {
                degree.value = 90f
            }


        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) = Unit

    }


    fun startListening() {
        sensorManager.registerListener(listener, rotationSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun stopListening() {
        sensorManager.unregisterListener(listener)
    }

}