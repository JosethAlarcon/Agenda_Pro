plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.josethcodedev.agenda_pro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.josethcodedev.agenda_pro"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.airbnb.android:lottie:6.4.0")
    implementation("com.google.firebase:firebase-bom:33.3.0")
    implementation("com.google.firebase:firebase-analytics:22.1.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    testImplementation(libs.junit)
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.android.volley:volley:1.2.1")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

