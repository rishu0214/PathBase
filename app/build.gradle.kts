plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id ("realm-android")
}

android {
    namespace = "com.traveleasy.pathbase"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.traveleasy.pathbase"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.1"
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation ("com.google.android.material:material:1.12.0")
    implementation ("com.loopj.android:android-async-http:1.4.10")

    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    implementation ("com.github.AnyChart:AnyChart-Android:1.1.5")

    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.0")


    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    implementation(files("C:\\Users\\rishu kumar\\Desktop\\Android Project\\PathBase\\lib\\json-simple-1.1.1.jar"))
    kapt("androidx.room:room-compiler:2.5.2")

    // Intuit SDP/SSP
    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.intuit.ssp:ssp-android:1.1.1")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.5")

    // Koin for Dependency Injection
    implementation("io.insert-koin:koin-android:3.4.2")

    // Retrofit for Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // ViewModel Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")

    // Image Loading
    implementation("io.coil-kt:coil:2.5.0")
    implementation("com.github.bumptech.glide:glide:4.12.0")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")

    // Rounded Image View
    implementation("com.makeramen:roundedimageview:2.3.0")

    // Testing Libraries
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
