plugins {
    id("java")
    id("application")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("org.mindrot:jbcrypt:0.4")
    implementation ("org.postgresql:postgresql:42.7.3")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.3")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.3")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("org.glassfish:jakarta.el:4.0.2")
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation ("org.slf4j:slf4j-api:2.0.7")
    implementation ("ch.qos.logback:logback-classic:1.5.13")
}

tasks.test {
    useJUnitPlatform()
}

