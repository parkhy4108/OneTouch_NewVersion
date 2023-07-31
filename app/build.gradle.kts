@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.dev_musashi.onetouch"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.dev_musashi.onetouch"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material3.window.size)
    implementation(libs.camera.core)
    implementation(libs.camera.camera2)
    implementation(libs.camera.view)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.extensions)
    implementation(libs.navigation.compose)
    implementation(libs.runtime)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.capturable)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.coroutine)
    implementation(libs.test.core)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.room.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    kapt(libs.hilt.compiler)
    ksp(libs.room.compiler)

}