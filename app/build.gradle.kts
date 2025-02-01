plugins {
    alias(libs.plugins.app.common)
    alias(libs.plugins.app.compose)
    alias(libs.plugins.app.hilt)
}

android {
    namespace = "com.same.alarm"

    defaultConfig {
        applicationId = "com.same.alarm"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":feature:main"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    debugImplementation(libs.androidx.ui.test.manifest)
}