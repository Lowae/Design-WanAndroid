plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("C:\\Users\\Lowae\\keystore\\Design WanAndroid.jks")
            storePassword = "990428"
            keyAlias = "design_wanandroid"
            keyPassword = "990428"
        }
    }
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
    }
}

dependencies {
    implementation(Deps.recyclerview)
    implementation(Deps.paging)
    implementation(Deps.pagingKtx)
    testImplementation(Deps.testJunit)
    androidTestImplementation(Deps.androidTestJunit)
    androidTestImplementation(Deps.androidTestEspresso)
}