plugins {
    alias(libs.plugins.lib.common)
    alias(libs.plugins.lib.compose)
    alias(libs.plugins.app.hilt)
    alias(libs.plugins.app.feature)
}

android {
    namespace = "com.same.alarm.main"
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":feature:setup"))
    implementation(project(":feature:feed"))
    implementation(project(":feature:list"))
    implementation(project(":feature:edit"))
    implementation(project(":core:navigation"))
    implementation(project(":core:model"))

    implementation(libs.tedpermission.normal)
}