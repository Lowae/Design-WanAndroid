plugins {
    id("com.android.library")
    kotlin("android")
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

    implementation("androidx.activity:activity-compose:1.5.0")
    implementation("androidx.compose.ui:ui:1.1.1")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.1.1")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.1.1")
    // Material Design
    implementation("androidx.compose.material:material:1.1.1")
    implementation("androidx.compose.material3:material3:1.0.0-alpha14")

    testImplementation(Deps.testJunit)
    androidTestImplementation(Deps.androidTestJunit)
    androidTestImplementation(Deps.androidTestEspresso)
}