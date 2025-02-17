plugins {
    alias(libs.plugins.lib.common)
    id("kotlinx-serialization")
}

android {
    namespace = "com.same.alarm.model"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}