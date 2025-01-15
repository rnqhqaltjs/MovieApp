plugins {
    alias(libs.plugins.lib.common)
}

android {
    namespace = "com.clone.movie.network"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.converter.scalars)
    implementation(libs.converter.moshi)
}