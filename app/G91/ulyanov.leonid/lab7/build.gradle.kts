plugins {
    id("java")
    id("war")
}

tasks.war {
    archiveBaseName.set("ulyanov-leonid")
    archiveFileName.set("ulyanov-leonid.war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation ("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation ("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation ("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet:jstl:1.2")
    implementation("org.mindrot:jbcrypt:0.4")
    testImplementation("org.mockito:mockito-core:5.11.0")
}

tasks.test {
    useJUnitPlatform()
}