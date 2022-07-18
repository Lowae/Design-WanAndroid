# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-verbose

# 保留代码行号
-keepattributes SourceFile,LineNumberTable

# Preserve some attributes that may be required for reflection.
-keepattributes AnnotationDefault,
                EnclosingMethod,
                InnerClasses,
                RuntimeVisibleAnnotations,
                RuntimeVisibleParameterAnnotations,
                RuntimeVisibleTypeAnnotations,
                Signature

-keep class * extends androidx.fragment.app.Fragment{}
-keep public class * extends androidx.preference.Preference

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep setters in Views so that animations can still work.
-keepclassmembers public class * extends android.view.View {
    void set*(***);
    *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick.
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# Preserve annotated Javascript interface methods.
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# The support libraries contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version. We know about them, and they are safe.
-dontnote android.support.**
-dontnote androidx.**
-dontwarn android.support.**
-dontwarn androidx.**

# Understand the @Keep support annotation.
-keep class androidx.annotation.Keep

-keep @androidx.annotation.Keep class * {*;}


-keepclasseswithmembers class * {
    @androidx.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @androidx.annotation.Keep <init>(...);
}

## Android architecture components: Lifecycle
# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements androidx.lifecycle.LifecycleObserver {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @androidx.lifecycle.OnLifecycleEvent *;
}

# ViewBinding
-keep public class * extends androidx.viewbinding.ViewBinding {*;}

# BottomSheetBehavior
-keepclassmembers public class com.google.android.material.bottomsheet.BottomSheetBehavior {
  void setStateInternal(int);
}

# These classes are duplicated between android.jar and org.apache.http.legacy.jar.
-dontnote org.apache.http.**
-dontnote android.net.http.**

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class com.lowe.wanandroid.services.model.** {*;}
-keepclasseswithmembers class com.lowe.wanandroid.base.http.adapter.NetworkResponse {*;}
-keepclasseswithmembers class * extends com.lowe.wanandroid.base.http.adapter.NetworkResponse {*;}

-keepclasseswithmembers class com.lowe.wanandroid.account.AccountState {*;}
-keepclasseswithmembers class com.lowe.wanandroid.account.LocalUserInfo {*;}
-keepclasseswithmembers class com.lowe.wanandroid.account.RegisterInfo {*;}

# AgentWeb
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**

# Databinding
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

# Gson
-keep class com.google.gson.** {*;}
-keep class com.google.gson.stream.** {*;}
-keep class com.google.** {
    <fields>;
    <methods>;
}