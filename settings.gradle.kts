pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            url = java.net.URI("https://maven.pkg.github.com/getzoop/zoop-package-public")
        }
    }
}

rootProject.name = "zoop-paymentinit-sample"
include(":app")
 