package convention.plugin

import com.android.build.api.dsl.LibraryExtension
import convention.plugin.extension.function.androidTestImplementation
import convention.plugin.extension.function.implementation
import convention.plugin.extension.function.testImplementation
import convention.plugin.extension.property.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")

            dependencies {
                implementation(libs.findLibrary("androidx.core.ktx").get())
                implementation(libs.findLibrary("androidx.appcompat").get())
                implementation(libs.findLibrary("material").get())
                testImplementation(libs.findLibrary("junit").get())
                androidTestImplementation(libs.findLibrary("androidx.junit").get())
                androidTestImplementation(libs.findLibrary("androidx.espresso.core").get())

                implementation(libs.findLibrary("jetbrains.kotlinx.serialization.json").get())
            }
        }

        target.extensions.configure<LibraryExtension> {
            compileSdk = 36

            defaultConfig {
                minSdk = 28

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                }
            }

            buildFeatures {
                buildConfig = true
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            when (this) {
                is KotlinAndroidProjectExtension -> compilerOptions
                is KotlinJvmProjectExtension -> compilerOptions
                else -> null
            }?.apply {
                jvmTarget = JvmTarget.JVM_21
            }
        }
    }
}
