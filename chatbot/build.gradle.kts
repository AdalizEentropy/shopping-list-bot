plugins {
    id ("org.springframework.boot")
    id("com.google.cloud.tools.jib")
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
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //--- telegram ---//
    implementation("org.telegram:telegrambots:$telegramVersion")
    implementation("org.telegram:telegrambotsextensions:$telegramVersion")

    //--- db ---//
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.postgresql:postgresql")
}

//------------------------//
// Plugins configuration  //
//------------------------//
jib {
    container.creationTime.set("USE_CURRENT_TIMESTAMP")
    from.image = "bellsoft/liberica-openjdk-alpine-musl:17.0.2-9"
    to {
        image = "adalizaentropy/shopping-list-bot"
        tags = setOf(project.version.toString())
    }
}
