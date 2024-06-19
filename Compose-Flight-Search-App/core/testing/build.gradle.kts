plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.example.flightsearchapp.core.testing"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    api(kotlin("test"))
    api(projects.core.data)

    implementation(projects.core.data)
    implementation(libs.androidx.core.ktx)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
