plugins {
    id("java")
    id("war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    implementation("org.postgresql:postgresql:42.7.5")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.2")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1")
    implementation("org.mindrot:jbcrypt:0.4")
    testImplementation("org.mockito:mockito-core:5.17.0")
}

tasks.test {
    useJUnitPlatform()
}