import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id ("org.springframework.boot")
    id("com.google.cloud.tools.jib")
}

val lombokVersion: String by project

dependencies {
    //--- lombok ---//
    implementation("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // ---spring ---//
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

//------------------------//
// Plugins configuration  //
//------------------------//
jib {
    container.creationTime.set("USE_CURRENT_TIMESTAMP")
    from.image = "bellsoft/liberica-openjdk-alpine-musl:17.0.2-9"
    to {
        image = "adalizaentropy/ai-client"
        tags = setOf(project.version.toString())
    }
}
