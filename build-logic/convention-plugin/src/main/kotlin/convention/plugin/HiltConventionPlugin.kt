package convention.plugin

import convention.plugin.extension.function.implementation
import convention.plugin.extension.function.ksp
import convention.plugin.extension.property.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.google.devtools.ksp")
            apply(plugin = "dagger.hilt.android.plugin")

            dependencies {
                implementation(libs.findLibrary("androidx.hilt.navigation.compose").get())
                implementation(libs.findLibrary("google.dagger.hilt.android").get())

                ksp(libs.findLibrary("androidx.hilt.compiler").get())
                ksp(libs.findLibrary("google.dagger.hilt.android.compiler").get())
            }
        }
    }
}
