// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("androidx.navigation.safeargs:androidx.navigation.safeargs.gradle.plugin:2.5.3")
    }
}

plugins {
    id("com.android.application") version "8.2.1" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}