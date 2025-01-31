plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.lib.compose)
    id("kotlinx-serialization")
}

android {
    namespace = "com.same.alarm.list"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.kotlinx.serialization.json)
}