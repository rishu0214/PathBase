buildscript {
    repositories {
        mavenCentral()
        }
    dependencies {
        classpath ("io.realm:realm-gradle-plugin:10.15.1")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
}