plugins {
    id("java")
    id("war")
}

tasks.war {
    archiveFileName.set("em.war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("org.slf4j:slf4j-api:1.7.36")
    runtimeOnly("ch.qos.logback:logback-classic:1.2.11")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet.jsp:javax.servlet.jsp-api:2.3.3")
    implementation("javax.servlet:jstl:1.2")
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.0.0")

    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0")
    runtimeOnly("org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1")

}

tasks.test {
    useJUnitPlatform()
}