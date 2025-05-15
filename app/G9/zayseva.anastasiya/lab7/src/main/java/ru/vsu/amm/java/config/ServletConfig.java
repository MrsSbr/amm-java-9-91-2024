package ru.vsu.amm.java.config;

import jakarta.servlet.Servlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import ru.vsu.amm.java.servlets.*;

public class ServletConfig {
    public static void registerServlets(Tomcat tomcat, Context ctx) {

        Tomcat.addServlet(ctx, "AuthServlet", (Servlet) new AuthServlet());
        ctx.addServletMappingDecoded("/auth/*", "AuthServlet");

        Tomcat.addServlet(ctx, "CourseServlet", (Servlet) new CourseServlet());
        ctx.addServletMappingDecoded("/courses/*", "CourseServlet");

        Tomcat.addServlet(ctx, "EnrollmentServlet", (Servlet) new EnrollmentServlet());
        ctx.addServletMappingDecoded("/enrollments/*", "EnrollmentServlet");

        Tomcat.addServlet(ctx, "MainServlet", (Servlet) new MainServlet());
        ctx.addServletMappingDecoded("/", "MainServlet");
        ctx.addServletMappingDecoded("/home", "MainServlet");

        Tomcat.addServlet(ctx, "UserServlet", (Servlet) new UserServlet());
        ctx.addServletMappingDecoded("/users/*", "UserServlet");

    }
}

