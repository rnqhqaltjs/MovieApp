package com.same.alarm.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.same.alarm.designsystem.theme.AlarmAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb()
            )
        )
        checkOverlayPermission()
    }

    private fun checkOverlayPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    requestIgnoreBatteryOptimizations(this@MainActivity)
                    setContent {
                        AlarmAppTheme {
                            MainScreen()
                        }
                    }
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(
                        this@MainActivity,
                        "화면 위에 표시 권한이 필요합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            .setDeniedMessage("알림 기능을 사용하려면\n설정 > 앱 > 권한에서 '다른 앱 위에 표시'를 허용해 주세요.")
            .setGotoSettingButton(true)
            .setGotoSettingButtonText("설정으로 이동")
            .setPermissions(android.Manifest.permission.SYSTEM_ALERT_WINDOW)
            .check()
    }

    @SuppressLint("BatteryLife")
    fun requestIgnoreBatteryOptimizations(context: Context) {
        val packageName = context.packageName
        val pm = context.getSystemService(POWER_SERVICE) as PowerManager

        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = "package:$packageName".toUri()
            context.startActivity(intent)
        }
    }
}