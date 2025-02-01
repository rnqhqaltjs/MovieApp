package extenstion

import com.android.build.api.dsl.CommonExtension
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.run {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("compose.compiler").get().toString()
        }

        dependencies {
            "implementation"(libs.findLibrary("androidx.activity.compose").get())
            "implementation"(platform(libs.findLibrary("androidx.compose.bom").get()))
            "implementation"(libs.findLibrary("androidx.ui").get())
            "implementation"(libs.findLibrary("androidx.ui.graphics").get())
            "implementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
            "implementation"(libs.findLibrary("androidx.material3").get())
            "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
        }
    }
}