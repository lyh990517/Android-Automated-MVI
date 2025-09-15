import java.util.Properties

plugins {
    alias(libs.plugins.convention.plugin.library)
    alias(libs.plugins.convention.plugin.dagger.hilt)
    alias(libs.plugins.convention.plugin.androidx.room)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
}

android {
    namespace = "com.yunho.data"

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"input base url here\"")
        buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("API_KEY", "")}\"")
    }
}

dependencies {
    implementation(projects.domain)

    implementation(libs.jakewharton.retrofit.retrofit2.kotlinx.serialization.converter)
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.squareup.okhttp3.okhttp)
    implementation(libs.squareup.retrofit2.retrofit)
}
