plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Version.compileSdk

    defaultConfig {
        applicationId = Version.applicationId
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
        versionCode = Version.versionCode
        versionName = Version.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        debug {
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    compileOptions {
        targetCompatibility(JavaVersion.VERSION_11)
        sourceCompatibility(JavaVersion.VERSION_11)
    }
}

dependencies {
    implementation(project(mapOf("path" to ":multityep")))

    implementation(Deps.coreKtx)
    implementation(Deps.appcompat)
    implementation(Deps.material)
    implementation(Deps.constraintlayout)
    implementation(Deps.lifecycleLiveDataKtx)
    implementation(Deps.lifecycleViewModelKtx)
    implementation(Deps.lifecucleRuntimeKtx)
    implementation(Deps.navigationFragmentKtx)
    implementation(Deps.navigationUiKtx)
    implementation(Deps.swiperefreshlayout)
    implementation(Deps.recyclerview)

    implementation(Deps.paging)
    implementation(Deps.pagingKtx)


    implementation(Deps.okhttp)
    implementation(Deps.okhttpLoggingInterceptor)
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonConverter)
    implementation(Deps.gson)
    implementation(Deps.fresco)

    implementation(Deps.banner)
    implementation(Deps.flexbox)

    implementation(Deps.agentWeb)
    implementation(Deps.dataStore)
    implementation(Deps.hiltAndroid)
    kapt(Deps.kaptHiltAndroidCompiler)

    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.preference:preference:1.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    testImplementation(Deps.testJunit)
    androidTestImplementation(Deps.androidTestJunit)
    androidTestImplementation(Deps.androidTestEspresso)
}