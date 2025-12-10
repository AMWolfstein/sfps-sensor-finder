package com.example.poweroverlay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val screenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_SCREEN_OFF -> {
                    Toast.makeText(context, "Power button pressed (screen OFF)", Toast.LENGTH_SHORT).show()
                }
                Intent.ACTION_SCREEN_ON -> {
                    Toast.makeText(context, "Power button pressed (screen ON)", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(OverlayView(this))

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
