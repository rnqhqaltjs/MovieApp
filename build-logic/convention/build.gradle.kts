import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.clone.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("AndroidCommonPlugin") {
            id = "clone.plugin.common"
            implementationClass = "AndroidCommonConventionPlugin"
        }
        register("AndroidHiltPlugin") {
            id = "clone.plugin.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("AndroidFeaturePlugin") {
            id = "clone.plugin.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}