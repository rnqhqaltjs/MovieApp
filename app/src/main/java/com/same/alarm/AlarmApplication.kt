package com.same.alarm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlarmApplication: Application() {
    override fun onCreate() {
        super.onCreate()


    }
}

private fun setKakao() {
//    KakaoSdk.init(this, "MrmthXS8wTHYIFoogF2dQ6GMRPw=")
}