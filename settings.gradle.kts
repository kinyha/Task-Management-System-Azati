rootProject.name = "task-management-system"

include(":src")
include(":frontend")

// Configure src project
project(":src").apply {
    projectDir = file("src")
    buildFileName = "build.gradle.kts"
}

// Configure frontend project
project(":frontend").apply {
    projectDir = file("frontend")
    buildFileName = "build.gradle.kts"
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}