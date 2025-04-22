plugins {
    id("java")
    id("war")
}

tasks.war {
    archiveFileName.set("ROOT_Nikita.war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.2")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("com.zaxxer:HikariCP:6.2.1")
    implementation ("org.slf4j:slf4j-api:2.0.17")
    implementation ("ch.qos.logback:logback-classic:1.5.18")
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

tasks.test {
    useJUnitPlatform()
}