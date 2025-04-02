plugins {
    id("java")
}

group = "ru.vsu.amm.java"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    // Тестирование
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Сервлеты и JSP
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet.jsp:javax.servlet.jsp-api:2.3.3")
    implementation("javax.servlet:jstl:1.2")

    // PostgreSQL JDBC драйвер
    implementation("org.postgresql:postgresql:42.7.3")

    // HikariCP для пула соединений
    implementation("com.zaxxer:HikariCP:5.0.1")

    // Шифрование паролей
    implementation("org.mindrot:jbcrypt:0.4")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    // Jakarta EE
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("jakarta.servlet.jsp:jakarta.servlet.jsp-api:3.0.0")

}

tasks.test {
    useJUnitPlatform()
}