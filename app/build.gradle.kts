plugins {
    alias(libs.plugins.convention.plugin.application)
    alias(libs.plugins.convention.plugin.dagger.hilt)
    alias(libs.plugins.convention.plugin.mvi.template)
}

android {
    namespace = "com.yunho.app"
}

dependencies {
    implementation(projects.feature)
    implementation(projects.data)
}
