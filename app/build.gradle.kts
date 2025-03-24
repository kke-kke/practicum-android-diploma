plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("ru.practicum.android.diploma.plugins.developproperties")
    id("com.google.devtools.ksp")
}

android {
    namespace = "ru.practicum.android.diploma"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.practicum.android.diploma"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", name = "HH_ACCESS_TOKEN", value = "\"${developProperties.hhAccessToken}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidX.navigation.fragment.ktx)
    implementation(libs.androidX.navigation.ui.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.androidX.room)
    implementation(libs.androidX.room.runtime)
    ksp(libs.androidX.room.compiler)

    implementation(libs.kotlinx.coroutines.android)

    // UI layer libraries
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Tools libraries
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.koin.android)
    implementation(libs.peko)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // region Unit tests
    testImplementation(libs.junit)
    // endregion

    // region UI tests
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // endregion
}
