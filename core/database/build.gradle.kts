plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.app.hilt)
}

android {
    namespace = "com.same.alarm.database"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":core:data"))

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}