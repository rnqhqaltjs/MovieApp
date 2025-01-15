plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.lib.compose)
}

android {
    namespace = "com.clone.movie.list"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
}