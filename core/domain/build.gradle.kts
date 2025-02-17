plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.app.hilt)
}

android {
    namespace = "com.same.alarm.domain"
}

dependencies {

    implementation(project(":core:model"))
}