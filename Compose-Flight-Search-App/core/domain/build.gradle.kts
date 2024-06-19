plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.example.flightsearchapp.core.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    ksp(libs.hilt.android.compiler)

    implementation(libs.hilt.android)

    implementation(projects.core.data)
    implementation(projects.core.model)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
