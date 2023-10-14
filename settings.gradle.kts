rootProject.name = "chat-bot"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
        mavenLocal()
    }

    val dependencyManagementVersion: String by settings
    val springframeworkBootVersion: String by settings
    val jibVersion: String by settings
    val spotlessVersion: String by settings
    val sonarlintVersion: String by settings

    plugins {
        id("io.spring.dependency-management") version dependencyManagementVersion
        id("org.springframework.boot") version springframeworkBootVersion
        id("com.google.cloud.tools.jib") version jibVersion
        id("com.diffplug.spotless") version spotlessVersion
        id("name.remal.sonarlint") version sonarlintVersion
    }
}
