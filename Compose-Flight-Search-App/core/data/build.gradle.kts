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
    namespace = "com.example.flightsearchapp.core.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    ksp(libs.hilt.android.compiler)

    api(projects.core.database)

    implementation(libs.hilt.android)
    implementation(libs.androidx.datastore.preferences)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(projects.core.testing)

}
