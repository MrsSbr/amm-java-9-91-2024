plugins {
    id("java")
    id("war")
}

tasks.war {
    archiveFileName.set("kanatnikov-maxim.war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet:jstl:1.2")
    testImplementation ("org.mockito:mockito-core:4.11.0")
    testImplementation ("org.mockito:mockito-junit-jupiter:4.11.0")
}

tasks.test {
    useJUnitPlatform()
}