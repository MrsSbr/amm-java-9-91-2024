plugins {
    id("java")
    id("war")
}

tasks.war{
    archiveFileName.set("kireev.dmitrii.war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.zaxxer:HikariCP:5.1.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation ("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation ("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation ("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet.jsp:javax.servlet.jsp-api:2.3.3")
    implementation("javax.servlet:jstl:1.2")
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.0.0")
}

tasks.test {
    useJUnitPlatform()
}