plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.lib.compose)
}

android {
    namespace = "com.same.alarm.designsystem"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
}