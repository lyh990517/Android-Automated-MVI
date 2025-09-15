plugins {
    alias(libs.plugins.convention.plugin.library)
    alias(libs.plugins.convention.plugin.compose)
    alias(libs.plugins.convention.plugin.dagger.hilt)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
}

android {
    namespace = "com.yunho.feature"
}

dependencies {
    implementation(projects.domain)
}
