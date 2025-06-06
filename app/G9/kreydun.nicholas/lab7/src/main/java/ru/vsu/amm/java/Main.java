package ru.vsu.amm.java;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import ru.vsu.amm.java.config.ServletConfig;
import ru.vsu.amm.java.services.Logg;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        String webappDirLocation = "app/G9/kreydun.nicholas/lab7/src/main/webapp";
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        tomcat.getConnector();
        tomcat.setBaseDir(new File("tomcat-work").getAbsolutePath());

        var ctx = tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

        ServletConfig.registerServlets(tomcat, ctx);

        Logg.logger.info("Starting Embedded Tomcat...");
        tomcat.start();
        tomcat.getServer().await();
    }
}