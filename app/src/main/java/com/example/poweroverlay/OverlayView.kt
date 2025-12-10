package com.example.poweroverlay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var overlayView: OverlayView

    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_SCREEN_ON || intent?.action == Intent.ACTION_SCREEN_OFF) {
                // Fetch sensor props when power button toggles screen
                val resId = resources.getIdentifier("config_sfps_sensor_props_0", "array", "android")
                if (resId != 0) {
                    val sensorProps = resources.getStringArray(resId)
                    // sensorProps[1] = X, sensorProps[2] = Y, sensorProps[3] = Radius
                    val x = sensorProps[1].toFloat()
                    val y = sensorProps[2].toFloat()
                    val r = sensorProps[3].toFloat()

                    overlayView.updateSensorProps(x, y, r)

                    Toast.makeText(
                        this@MainActivity,
                        "Sensor detected at X=$x, Y=$y, R=$r",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this@MainActivity, "Sensor props not found", Toast.LENGTH_SHORT).show()
                }
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
}
