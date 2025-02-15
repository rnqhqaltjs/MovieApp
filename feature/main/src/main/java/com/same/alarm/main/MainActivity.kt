package com.same.alarm.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.same.alarm.designsystem.theme.AlarmAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val REQUEST_CODE_EXACT_ALARM = 1001
    private val REQUEST_CODE_USE_EXACT_ALARM = 1002
    private val REQUEST_CODE_OVERLAY_PERMISSION = 1003

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlarmAppTheme {
                MainScreen()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM)
                        != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.SCHEDULE_EXACT_ALARM),
                            REQUEST_CODE_EXACT_ALARM
                        )
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_EXACT_ALARM)
                        != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.USE_EXACT_ALARM),
                            REQUEST_CODE_USE_EXACT_ALARM
                        )
                    }
                }

                // SYSTEM_ALERT_WINDOW 권한 요청
                if (!Settings.canDrawOverlays(this)) {
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    intent.data = Uri.parse("package:$packageName")
                    startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION)
                }
            }
        }
    }
}