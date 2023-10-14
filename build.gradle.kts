plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.diffplug.spotless")
    id("name.remal.sonarlint")
}

group = "ru.adaliza"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val lombokVersion: String by project
val telegramVersion: String by project

dependencies {
    // ---spring ---//
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //--- lombok ---//
    implementation("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    //--- telegram ---//
    implementation("org.telegram:telegrambots:$telegramVersion")
    implementation("org.telegram:telegrambotsextensions:$telegramVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

configurations.all {
    resolutionStrategy {
        failOnVersionConflict()

        force("org.sonarsource.analyzer-commons:sonar-analyzer-commons:2.4.0.1317")
        force("com.google.code.findbugs:jsr305:3.0.2")
        force("org.sonarsource.sslr:sslr-core:1.24.0.633")
        force("org.eclipse.platform:org.eclipse.osgi:3.18.300")
    }
}
