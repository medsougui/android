// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.0") // Replace with your Gradle version
        classpath("com.google.gms:google-services:4.4.0") // Add this line here
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
}
