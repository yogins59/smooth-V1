package com.smooth60

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var projectionManager: MediaProjectionManager
    private lateinit var status: TextView
    private val requestCode = 6001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        status = findViewById(R.id.statusText)
        val button = findViewById<Button>(R.id.startButton)
        projectionManager = getSystemService(MediaProjectionManager::class.java)

        button.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")))
                status.text = "Status: Izinkan overlay, lalu tekan START lagi."
                return@setOnClickListener
            }
            startActivityForResult(projectionManager.createScreenCaptureIntent(), requestCode)
        }
    }

    @Deprecated("Deprecated Android API; retained for simple prototype compatibility.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != this.requestCode || resultCode != RESULT_OK || data == null) {
            status.text = "Status: CAPTURE DIBATALKAN"
            return
        }

        val serviceIntent = Intent(this, CaptureService::class.java).apply {
            putExtra("resultCode", resultCode)
            putExtra("data", data)
        }
        startForegroundService(serviceIntent)
        status.text = "Status: CAPTURE RUNNING"
    }
}
