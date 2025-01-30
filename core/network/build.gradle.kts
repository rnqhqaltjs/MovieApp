plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.app.hilt)
}

android {
    namespace = "com.same.alarm.network"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.okhttp)
    implementation(libs.moshi.kotlin)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.scalars)
    implementation(libs.converter.moshi)
}