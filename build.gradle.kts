// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath ("com.android.tools.build:gradle:3.4.0")
        classpath ("com.google.gms:google-services:4.3.14")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false

}