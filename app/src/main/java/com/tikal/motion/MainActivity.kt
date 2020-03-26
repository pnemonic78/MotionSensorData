package com.tikal.motion

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    protected lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onStart() {
        super.onStart()

        monitor(Sensor.TYPE_ACCELEROMETER)
        monitor(Sensor.TYPE_GYROSCOPE)
        monitor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    private fun monitor(type: Int) {
        val sensor = sensorManager.getDefaultSensor(type)
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        val data = event.values

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                value_accelerometer_x.text = getString(R.string.value_g, data[0] / EARTH_GRAVITY)
                value_accelerometer_y.text = getString(R.string.value_g, data[1] / EARTH_GRAVITY)
                value_accelerometer_z.text = getString(R.string.value_g, data[2] / EARTH_GRAVITY)

                value_motionGravity_x.text = getString(R.string.value_g, data[0] / EARTH_GRAVITY)
                value_motionGravity_y.text = getString(R.string.value_g, data[1] / EARTH_GRAVITY)
                value_motionGravity_z.text = getString(R.string.value_g, data[2] / EARTH_GRAVITY)
            }
            Sensor.TYPE_GYROSCOPE -> {
                value_gyroscope_x.text = getString(R.string.value_radians_per_sec, data[0])
                value_gyroscope_y.text = getString(R.string.value_radians_per_sec, data[1])
                value_gyroscope_z.text = getString(R.string.value_radians_per_sec, data[2])

                value_motionRotation_x.text = getString(R.string.value_radians_per_sec, data[0])
                value_motionRotation_y.text = getString(R.string.value_radians_per_sec, data[1])
                value_motionRotation_z.text = getString(R.string.value_radians_per_sec, data[2])
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                value_magnetometer_x.text = getString(R.string.value_micro_tesla, data[0])
                value_magnetometer_y.text = getString(R.string.value_micro_tesla, data[1])
                value_magnetometer_z.text = getString(R.string.value_micro_tesla, data[2])
            }
        }
    }

    companion object {
        const val EARTH_GRAVITY = -9.81f
    }
}
