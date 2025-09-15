plugins {
    `kotlin-dsl`
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
}

group = "com.yunho.app.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.androidx.room.gradle.plugin)
    compileOnly(libs.google.dagger.hilt.android.gradlePlugin)
    compileOnly(libs.google.devtools.ksp.gradlePlugin)
    implementation(libs.jetbrains.kotlinx.serialization.json)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("application") {
            id = "convention.plugin.application"
            implementationClass = "convention.plugin.AndroidApplicationConventionPlugin"
        }
        register("library") {
            id = "convention.plugin.library"
            implementationClass = "convention.plugin.AndroidLibraryConventionPlugin"
        }
        register("hilt") {
            id = "convention.plugin.dagger.hilt"
            implementationClass = "convention.plugin.HiltConventionPlugin"
        }
        register("room") {
            id = "convention.plugin.androidx.room"
            implementationClass = "convention.plugin.AndroidxRoomConventionPlugin"
        }
        register("compose") {
            id = "convention.plugin.compose"
            implementationClass = "convention.plugin.AndroidxComposeConventionPlugin"
        }
        register("mvi.template") {
            id = "convention.plugin.mvi.template"
            implementationClass = "convention.plugin.MviTemplatePlugin"
        }
    }
}
