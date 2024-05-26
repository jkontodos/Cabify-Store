plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "com.jkontodos.cabifystore"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jkontodos.cabifystore"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://gist.githubusercontent.com/\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        buildConfig = true
    }
    dataBinding.enable = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.lottie)
    implementation(libs.shimmer)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.gson)
    implementation(libs.squareup.interceptor)
    implementation(libs.hilt.android)
    kapt (libs.hilt.android.compiler)
    implementation(libs.splashscreen)

    testImplementation(libs.mockito)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.junit)
    testImplementation(libs.amshove.kluent)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}