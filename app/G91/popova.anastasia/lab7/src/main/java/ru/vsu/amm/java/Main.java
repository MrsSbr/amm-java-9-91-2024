package ru.vsu.amm.java;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.jasper.servlet.JasperInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import ru.vsu.amm.java.DB_config.DatabaseConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Set;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
                SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        log.info("JUL to SLF4J bridge installed");

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
        tomcat.setBaseDir("tomcat");

        Path webappPath = Paths
                .get("app", "G91", "popova.anastasia", "lab7", "src", "main", "webapp")
                .toAbsolutePath();

        if (!Files.isDirectory(webappPath)) {
            throw new RuntimeException("Webapp directory not found: " + webappPath);
        }

        try {
            Context ctx = tomcat.addWebapp("", webappPath.toString());
            ctx.addServletContainerInitializer(new JasperInitializer(), Set.of());

            log.info("Webapp added at path: {}", webappPath);

            tomcat.start();
            log.info("Tomcat started successfully on port 8080");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    log.info("Shutting down Tomcat...");
                    tomcat.stop();
                    tomcat.destroy();
                    DatabaseConnection.shutdown();
                    log.info("Tomcat and database pool stopped");
                } catch (LifecycleException e) {
                    log.error("Error during Tomcat shutdown", e);
                }
            }));

            tomcat.getServer().await();
        } catch (Exception e) {
            log.error("Application startup failed", e);
            throw new RuntimeException("Application startup failed", e);
        }
    }

}