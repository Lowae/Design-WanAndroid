import java.nio.charset.Charset

private const val verName = "1.0"
private val verCode: Int by lazy { "git rev-list --count HEAD".exec().toInt() }

object Version {
    object ClassPathVersion {
        const val hiltPluginVersion = "2.42"
        const val hiltCompilerVersion = "1.0.0"
    }

    const val compileSdk = 32
    const val applicationId = "com.lowe.wanandroid"
    const val minSdk = 23
    const val targetSdk = 32
    val versionCode = verCode
    const val versionName = verName

    const val coreKtxVersion = "1.8.0"
    const val appCompatVersion = "1.4.2"
    const val materialVersion = "1.6.1"
    const val constraintLayoutVersion = "2.1.4"
    const val lifecycleVersion = "2.4.1"
    const val navigationVersion = "2.4.2"
    const val swipeRefreshLayoutVersion = "1.1.0"
    const val recyclerViewVersion = "1.3.0-alpha02"
    const val preferenceVersion = "1.2.0"

    // paging3
    const val pagingVersion = "3.1.1"

    // 网络
    const val retrofitVersion = "2.9.0"
    const val okHttp3Version = "4.9.3"
    const val gsonVersion = "2.9.0"
    const val frescoVersion = "2.6.0"

    const val testJunitVersion = "4.13.2"
    const val androidTestJunitAndroidExt = "1.1.3"
    const val androidTestEspressoCore = "3.4.0"

    // ui
    const val bannerVersion = "2.2.2"
    const val flexboxVersion = "3.0.0"

    const val agentWebVersion = "v4.1.9-androidx"
    const val dataStoreVersion = "1.0.0"

    const val kotlinSerialVersion = "1.3.3"
}

object Deps {

    object ClassPath {

        const val hiltPlugin =
            "com.google.dagger:hilt-android-gradle-plugin:${Version.ClassPathVersion.hiltPluginVersion}"
    }

    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtxVersion}"
    const val appcompat = "androidx.appcompat:appcompat:${Version.appCompatVersion}"
    const val material = "com.google.android.material:material:${Version.materialVersion}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayoutVersion}"
    const val lifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycleVersion}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleVersion}"
    const val lifecucleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleVersion}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Version.navigationVersion}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Version.navigationVersion}"
    const val swiperefreshlayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefreshLayoutVersion}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Version.recyclerViewVersion}"
    const val preferences = "androidx.preference:preference:${Version.preferenceVersion}"
    const val testJunit = "junit:junit:${Version.testJunitVersion}"
    const val androidTestJunit = "androidx.test.ext:junit:${Version.androidTestJunitAndroidExt}"
    const val androidTestEspresso =
        "androidx.test.espresso:espresso-core:${Version.androidTestEspressoCore}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okHttp3Version}"
    const val okhttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Version.okHttp3Version}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
    const val retrofitGsonConverter =
        "com.squareup.retrofit2:converter-gson:${Version.retrofitVersion}"
    const val gson = "com.google.code.gson:gson:${Version.gsonVersion}"
    const val fresco = "com.facebook.fresco:fresco:${Version.frescoVersion}"
    const val banner = "io.github.youth5201314:banner:${Version.bannerVersion}"
    const val flexbox = "com.google.android.flexbox:flexbox:${Version.flexboxVersion}"
    const val paging = "androidx.paging:paging-runtime:${Version.pagingVersion}"
    const val pagingKtx = "androidx.paging:paging-runtime-ktx:${Version.pagingVersion}"
    const val agentWeb = "com.github.Justson.AgentWeb:agentweb-core:${Version.agentWebVersion}"
    const val dataStore = "androidx.datastore:datastore-preferences:${Version.dataStoreVersion}"
    const val hiltAndroid =
        "com.google.dagger:hilt-android:${Version.ClassPathVersion.hiltPluginVersion}"
    const val kotlinSerial =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.kotlinSerialVersion}"
    const val kaptHiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Version.ClassPathVersion.hiltPluginVersion}"
    const val kaptHiltCompiler =
        "androidx.hilt:hilt-compiler:${Version.ClassPathVersion.hiltCompilerVersion}"
}

fun String.exec(): String =
    Runtime.getRuntime().exec(this).inputStream.readBytes().toString(Charset.defaultCharset())
        .trim()