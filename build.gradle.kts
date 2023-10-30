plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.diffplug.spotless")
    id("name.remal.sonarlint")
    id("com.google.cloud.tools.jib")
}

group = "ru.adaliza"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val lombokVersion: String by project
val telegramVersion: String by project
val liquibaseVersion: String by project
val postgresqlVersion: String by project

dependencies {
    //--- lombok ---//
    implementation("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // ---spring ---//
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //--- telegram ---//
    implementation("org.telegram:telegrambots:$telegramVersion")
    implementation("org.telegram:telegrambotsextensions:$telegramVersion")
//    implementation("org.telegram:telegrambots-abilities:$telegramVersion")

    //--- db ---//
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.postgresql:postgresql")
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

//------------------------//
// Plugins configuration  //
//------------------------//
jib {
    container.creationTime.set("USE_CURRENT_TIMESTAMP")
    from.image = "bellsoft/liberica-openjdk-alpine-musl:17.0.2-9"
    to {
        image = "adalizaentropy/chat-bot"
        tags = setOf(project.version.toString())
    }
}
