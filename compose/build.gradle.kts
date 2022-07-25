plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.lowe.compose"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
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
    implementation(project(mapOf("path" to ":common")))

    implementation(Deps.material)

    implementation(Deps.hiltAndroid)
    kapt(Deps.kaptHiltAndroidCompiler)
    kapt(Deps.kaptHiltCompiler)

    // Compose
    implementation(Deps.ComposeDependency.composeActivity)
    implementation(Deps.ComposeDependency.composeUI)
    // Tooling support (Previews, etc.)
    implementation(Deps.ComposeDependency.composeTool)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(Deps.ComposeDependency.composeFoundation)
    // Material Design
    implementation(Deps.ComposeDependency.composeMaterial3)
    implementation(Deps.ComposeDependency.composeMaterial3Window)

    testImplementation(Deps.testJunit)
    androidTestImplementation(Deps.androidTestJunit)
    androidTestImplementation(Deps.androidTestEspresso)
}