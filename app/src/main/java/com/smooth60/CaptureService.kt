package com.smooth60

import android.app.*
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.IBinder
import android.view.WindowManager
import android.widget.TextView

class CaptureService : Service() {
    private var overlay: TextView? = null
    private var windowManager: WindowManager? = null

    override fun onCreate() {
        super.onCreate()
        createChannel()
        startForeground(60, Notification.Builder(this, "smooth60")
            .setContentTitle("Smooth60 aktif")
            .setContentText("Screen capture engine berjalan")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .build())

        showOverlay()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // MediaProjection session wiring will be added in the next prototype step.
        // Keeping this service stable lets us first verify overlay + foreground execution.
        return START_STICKY
    }

    private fun showOverlay() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        overlay = TextView(this).apply {
            text = "Smooth60\nFPS: --"
            textSize = 14f
            setPadding(22, 14, 22, 14)
            setBackgroundColor(0xCCFFFFFF.toInt())
            setTextColor(0xFF111111.toInt())
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            android.graphics.PixelFormat.TRANSLUCENT
        )
        params.x = 30
        params.y = 180
        windowManager?.addView(overlay, params)
    }

    private fun createChannel() {
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(
            NotificationChannel("smooth60", "Smooth60",
                NotificationManager.IMPORTANCE_LOW)
        )
    }

    override fun onDestroy() {
        overlay?.let { windowManager?.removeView(it) }
        overlay = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
