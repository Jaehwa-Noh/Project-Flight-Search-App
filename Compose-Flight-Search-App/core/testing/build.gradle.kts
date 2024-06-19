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
    namespace = "com.example.flightsearchapp.core.testing"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    ksp(libs.hilt.android.compiler)

    api(kotlin("test"))

    api(projects.core.data)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.test.rules)
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.testing)
    implementation(libs.kotlinx.coroutines.test)

    implementation(projects.core.data)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
