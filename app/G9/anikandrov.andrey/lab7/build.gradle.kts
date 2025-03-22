plugins {
    id("java")
    id("war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

tasks.war {
    archiveFileName.set("ROOT.war")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet.jsp:javax.servlet.jsp-api:2.3.3")
    implementation("javax.servlet:jstl:1.2")
}

tasks.test {
    useJUnitPlatform()
}