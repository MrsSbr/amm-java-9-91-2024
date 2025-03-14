plugins {
    id("java")
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
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}