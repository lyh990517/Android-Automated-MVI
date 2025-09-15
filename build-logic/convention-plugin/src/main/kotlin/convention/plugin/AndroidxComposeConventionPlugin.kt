package convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import convention.plugin.extension.function.androidTestImplementation
import convention.plugin.extension.function.debugImplementation
import convention.plugin.extension.function.implementation
import convention.plugin.extension.property.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidxComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = with(extensions) {
                findByType<ApplicationExtension>() ?: findByType<LibraryExtension>()
            }

            extension?.apply {
                buildFeatures {
                    compose = true
                }
            }

            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            tasks.withType<KotlinCompile>().configureEach {
                val composeReportsDir = layout.buildDirectory.get().asFile.absolutePath + "/compose_compiler"

                compilerOptions.freeCompilerArgs.addAll(
                    listOf(
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$composeReportsDir",
                        "-P",
                        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$composeReportsDir"
                    )
                )
            }

            dependencies {
                androidTestImplementation(libs.findLibrary("androidx.ui.test.junit4").get())
                androidTestImplementation(platform(libs.findLibrary("androidx.compose.bom").get()))

                debugImplementation(libs.findLibrary("androidx.ui.test.manifest").get())
                debugImplementation(libs.findLibrary("androidx.ui.tooling").get())

                implementation(libs.findLibrary("androidx.activity.compose").get())
                implementation(libs.findLibrary("androidx.compose.animation.graphics").get())
                implementation(libs.findLibrary("androidx.compose.material.icons.extended").get())
                implementation(libs.findLibrary("androidx.material3").get())
                implementation(libs.findLibrary("androidx.ui").get())
                implementation(libs.findLibrary("androidx.ui.graphics").get())
                implementation(libs.findLibrary("androidx.ui.tooling.preview").get())
                implementation(libs.findLibrary("androidx.navigation.compose").get())
                implementation(libs.findLibrary("androidx.paging.compose").get())
                implementation(platform(libs.findLibrary("androidx.compose.bom").get()))
                implementation(libs.findLibrary("coil.compose").get())
                implementation(libs.findLibrary("coil.network.okhttp").get())

                implementation(libs.findLibrary("orbit.mvi.compose").get())
                implementation(libs.findLibrary("orbit.mvi.core").get())
                implementation(libs.findLibrary("orbit.mvi.viewmodel").get())
            }
        }
    }
}
