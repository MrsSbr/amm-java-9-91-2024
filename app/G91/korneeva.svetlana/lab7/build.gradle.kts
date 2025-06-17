plugins {
    id("java")
    id("war")
}

tasks.war {
    archiveFileName.set("ROOT_Svetlana.war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.6.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation ("org.mockito:mockito-core:5.16.1")
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
    implementation ("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.2")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    implementation ("org.slf4j:slf4j-api:2.0.17")
    implementation ("ch.qos.logback:logback-classic:1.5.18")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

tasks.test {
    useJUnitPlatform()
}