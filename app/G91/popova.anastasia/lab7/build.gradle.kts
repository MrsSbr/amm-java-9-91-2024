plugins {
    id("java")
    id("application")
}

group = "ru.vsu.amm.java"
version = "1.0"

application {
    mainClass.set("ru.vsu.amm.java.Main")
    applicationDefaultJvmArgs = listOf(
        "--add-opens", "java.base/java.io=ALL-UNNAMED",
        "--add-opens", "java.base/java.lang=ALL-UNNAMED",
        "--add-opens", "java.base/java.util=ALL-UNNAMED",
        "--add-opens", "java.base/java.util.concurrent=ALL-UNNAMED",
        "-Dorg.apache.catalina.level=FINE",
        "-Dorg.apache.catalina.startup.level=FINE"
    )
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    implementation("org.projectlombok:lombok:1.18.32");

    // Основные зависимости
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("com.zaxxer:HikariCP:5.1.0")

    // Логирование
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.5.13")
    implementation("org.slf4j:jul-to-slf4j:2.0.13")

    // Tomcat
    implementation("org.apache.tomcat.embed:tomcat-embed-core:11.0.3")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:11.0.3")

    // Валидация
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
    implementation("org.glassfish:jakarta.el:4.0.2")

    // Servlet API
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0")
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.0")

    // Прочие
    implementation("org.jetbrains:annotations:24.0.1")
}

val cleanWebappClasses by tasks.register<Delete>("cleanWebappClasses") {
    delete("src/main/webapp/WEB-INF/classes")
}

val syncClasses by tasks.register<Copy>("syncClassesTask") {
    from(layout.buildDirectory.dir("classes/java/main"))
    into("src/main/webapp/WEB-INF/classes")
    dependsOn(tasks.classes)
}

tasks.named("run") {
    dependsOn(cleanWebappClasses, syncClasses)
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}