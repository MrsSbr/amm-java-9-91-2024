plugins {
    id("java")
    id("war")
}

group = "ru.vsu.amm.java"
version = "unspecified"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
}

dependencies {

    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.19")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:10.1.19")

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.postgresql:postgresql:42.7.5")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.war {
    archiveFileName.set("app.war")
}