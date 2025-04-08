plugins {
    id("java")
    id("war")
}

group = "ru.vsu.amm.java"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation ("org.mockito:mockito-core:5.7.0")
    implementation("org.apache.tomcat:tomcat-jasper:10.1.39")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.postgresql:postgresql:42.7.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}