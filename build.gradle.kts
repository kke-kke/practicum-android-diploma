// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version libs.versions.kotlin.get() apply false
    id("convention.detekt")
    id("com.google.devtools.ksp") version "1.9.24-1.0.20" apply false
}
