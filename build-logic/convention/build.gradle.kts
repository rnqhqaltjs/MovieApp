import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.clone.movie.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
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
        register("AndroidApplicationCommonPlugin") {
            id = "app.plugin.common"
            implementationClass = "AndroidApplicationCommonConventionPlugin"
        }
        register("AndroidLibraryCommonPlugin") {
            id = "lib.plugin.common"
            implementationClass = "AndroidLibraryCommonConventionPlugin"
        }
        register("AndroidApplicationComposePlugin") {
            id = "app.plugin.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("AndroidLibraryComposePlugin") {
            id = "lib.plugin.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
//        register("AndroidHiltPlugin") {
//            id = "clone.plugin.hilt"
//            implementationClass = "AndroidHiltConventionPlugin"
//        }
//        register("AndroidFeaturePlugin") {
//            id = "clone.plugin.feature"
//            implementationClass = "AndroidFeatureConventionPlugin"
//        }
    }
}