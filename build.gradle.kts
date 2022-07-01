// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath(kotlin("gradle-plugin", "1.7.0"))
        classpath(kotlin("serialization", "1.7.0"))
        classpath(Deps.ClassPath.hiltPlugin)
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}