package com.same.alarm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.kakao.sdk.common.KakaoSdk
import com.same.alarm.BuildConfig

@HiltAndroidApp
class AlarmApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.NATIVE_APP_KEY)
    }
}