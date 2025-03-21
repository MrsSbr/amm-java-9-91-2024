plugins {
    id("java")
    id("war")
}

group = "ru.vsu.amm.java"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation ("org.slf4j:slf4j-simple:2.0.7")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.18")
    implementation ("org.apache.tomcat.embed:tomcat-embed-jasper:10.1.18")
    testImplementation ("org.mockito:mockito-core:5.7.0")
    implementation("org.apache.tomcat:tomcat-jasper:10.1.39")
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.postgresql:postgresql:42.7.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}