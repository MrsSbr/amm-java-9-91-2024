plugins {
    id("java")
    id("war")
}

tasks.war {
    archiveBaseName.set("simonov.ivan-lab7")
    archiveFileName.set("simonov.ivan-lab7.war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet:jstl:1.2")
    implementation("org.postgresql:postgresql:42.7.5")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}