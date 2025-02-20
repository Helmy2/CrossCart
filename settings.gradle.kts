rootProject.name = "CrossCart"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
                maven("https://packages.jetbrains.team/maven/p/firework/dev")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
                maven("https://packages.jetbrains.team/maven/p/firework/dev")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}