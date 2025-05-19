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
    implementation ("org.slf4j:slf4j-simple:2.0.9")
    implementation ("org.jetbrains:annotations:23.0.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet.jsp:javax.servlet.jsp-api:2.3.3")
    implementation("javax.servlet:jstl:1.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.h2database:h2:2.1.214")
    testImplementation 'org.apache.tomcat.embed:tomcat-embed-core:10.1.13'
    testImplementation 'org.apache.tomcat.embed:tomcat-embed-jasper:10.1.13'
    testImplementation 'javax.servlet:javax.servlet-api:4.0.1'
    testImplementation("com.h2database:h2:2.1.214")
}

tasks.test {
    useJUnitPlatform()
}