package com.example.poweroverlay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var overlayView: OverlayView
    private var pendingContent: String? = null

    // Register a file picker for saving
    private val createFileLauncher = registerForActivityResult(
        ActivityResultContracts.CreateDocument("text/plain")
    ) { uri: Uri? ->
        uri?.let {
            saveTextToUri(it, pendingContent ?: "")
            Toast.makeText(this, "Saved sensor props to chosen file", Toast.LENGTH_LONG).show()
        }
    }

    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_SCREEN_ON || intent?.action == Intent.ACTION_SCREEN_OFF) {
                saveSensorProps()
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

    private fun saveSensorProps() {
        val resId = resources.getIdentifier("config_sfps_sensor_props_0", "array", "android")
        if (resId != 0) {
            val sensorProps = resources.getStringArray(resId)
            val x = sensorProps[1]
            val y = sensorProps[2]
            val r = sensorProps[3]

            overlayView.updateSensorProps(x.toFloat(), y.toFloat(), r.toFloat())

            pendingContent = "Sensor Props:\nX=$x\nY=$y\nR=$r\n"

            // Launch system file picker so user chooses location
            createFileLauncher.launch("sensor_props.txt")
        } else {
            Toast.makeText(this, "Sensor props not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveTextToUri(uri: Uri, content: String) {
        contentResolver.openOutputStream(uri)?.use { outputStream: OutputStream ->
            outputStream.write(content.toByteArray())
        }
    }
}
