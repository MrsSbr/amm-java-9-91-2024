plugins {
    id("java")
    id("war")
}

tasks.war {
    archiveFileName.set("barbashina-irina.war")
    webInf {
        from("src/main/webapp/WEB-INF") {
            include("web.xml", "**/*.tld")
        }
        from("src/main/resources") {
            include("*.properties", "*.xml")
        }
    }
}

group = "ru.vsu.amm.java"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:10.0.27")

    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.mindrot:jbcrypt:0.4")

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:2.0.0")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:2.0.0")
}

tasks.test {
    useJUnitPlatform()
}