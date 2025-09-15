plugins {
    alias(libs.plugins.convention.plugin.library)
}

android {
    namespace = "com.yunho.domain"
}

dependencies {
    implementation(libs.javax.inject)
}
