plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.app.hilt)
}

android {
    namespace = "com.same.alarm.infra"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":feature:ring"))
}