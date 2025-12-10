package com.example.poweroverlay

import android.accessibilityservice.AccessibilityService
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class PowerButtonService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_POWER) {
            if (event.action == KeyEvent.ACTION_DOWN) {
                Toast.makeText(this, "Power button pressed", Toast.LENGTH_SHORT).show()
            }
            if (event.isLongPress) {
                Toast.makeText(this, "Power button long‑press detected!", Toast.LENGTH_LONG).show()
            }
            return true
        }
        return super.onKeyEvent(event)
    }
}
