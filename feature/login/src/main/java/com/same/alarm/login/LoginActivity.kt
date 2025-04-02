package com.same.alarm.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.same.alarm.designsystem.theme.AlarmAppTheme
import com.same.alarm.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AlarmAppTheme {
                LoginRoute(
                    onLoginSuccess = {
                        navigateToMain()
                    }
                )
            }
        }
    }

    private fun navigateToMain() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }
}