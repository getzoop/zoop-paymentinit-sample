// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}
allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = java.net.URI("https://maven.pkg.github.com/getzoop/zoop-package-public")
            credentials {
                username = project.findProperty("GITHUB_USER") as String? ?: System.getenv("GITHUB_USER")
                password = project.findProperty("GITHUB_PAT") as String? ?: System.getenv("GITHUB_PAT")
            }
        }
    }
}