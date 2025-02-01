plugins {
    alias(libs.plugins.lib.common)
    id("kotlinx-serialization")
}

android {
    namespace = "com.same.alarm.navigation"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}