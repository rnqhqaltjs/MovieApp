plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.lib.compose)
}

android {
    namespace = "com.same.alarm.list"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    debugImplementation(libs.androidx.ui.tooling)
}