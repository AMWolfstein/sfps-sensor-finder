package com.example.poweroverlay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var overlayView: OverlayView

    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_SCREEN_ON || intent?.action == Intent.ACTION_SCREEN_OFF) {
                saveSensorPropsToFile()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overlayView = OverlayView(this)
        setContentView(overlayView)

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
        }
        registerReceiver(screenReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(screenReceiver)
    }

    private fun saveSensorPropsToFile() {
        val resId = resources.getIdentifier("config_sfps_sensor_props_0", "array", "android")
        if (resId != 0) {
            val sensorProps = resources.getStringArray(resId)
            val x = sensorProps[1]
            val y = sensorProps[2]
            val r = sensorProps[3]

            // Update overlay
            overlayView.updateSensorProps(x.toFloat(), y.toFloat(), r.toFloat())

            // Save to file in app's internal storage
            val fileName = "sensor_props.txt"
            val file = File(filesDir, fileName)
            val content = "Sensor Props:\nX=$x\nY=$y\nR=$r\n"
            FileOutputStream(file).use {
                it.write(content.toByteArray())
            }

            Toast.makeText(this, "Saved sensor props to $fileName", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Sensor props not found", Toast.LENGTH_SHORT).show()
        }
    }
}
