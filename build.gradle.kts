// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

buildscript {
    extra.apply {
        set("GITHUB_USER", project.findProperty("GITHUB_USER") as String? ?: System.getenv("GITHUB_USER"))
        set("GITHUB_PAT", project.findProperty("GITHUB_PAT") as String? ?: System.getenv("GITHUB_PAT"))
    }

    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        maven {
            url = java.net.URI("https://maven.pkg.github.com/getzoop/zoop-package-public")
            credentials {
                username = extra.get("GITHUB_USER") as String
                password = extra.get("GITHUB_PAT") as String

            }
        }
    }
}