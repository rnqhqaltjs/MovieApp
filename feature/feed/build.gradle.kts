plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.lib.compose)
    alias(libs.plugins.app.feature)
}

android {
    namespace = "com.same.alarm.feed"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
}