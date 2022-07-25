plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.lowe.common"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(project(mapOf("path" to ":resource")))
    implementation(Deps.lifecucleRuntimeKtx)

    implementation(Deps.paging)
    implementation(Deps.pagingKtx)

    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonConverter)
    implementation(Deps.okhttp)
    implementation(Deps.okhttpLoggingInterceptor)

    implementation(Deps.preferences)
    implementation(Deps.hiltAndroid)
    kapt(Deps.kaptHiltAndroidCompiler)
    kapt(Deps.kaptHiltCompiler)

    implementation(Deps.dataStore)
    implementation(Deps.kotlinSerial)

    testImplementation(Deps.testJunit)
    androidTestImplementation(Deps.androidTestJunit)
    androidTestImplementation(Deps.androidTestEspresso)
}