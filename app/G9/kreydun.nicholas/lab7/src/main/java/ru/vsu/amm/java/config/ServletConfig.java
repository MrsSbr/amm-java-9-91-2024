package ru.vsu.amm.java.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import ru.vsu.amm.java.servlet.*;

public class ServletConfig {
    public static void registerServlets(Tomcat tomcat, Context ctx) {
        Tomcat.addServlet(ctx, "CreatePostServlet", new CreatePostServlet());
        ctx.addServletMappingDecoded("/createPost", "CreatePostServlet");

        Tomcat.addServlet(ctx, "CreateUserServlet", new CreateUserServlet());
        ctx.addServletMappingDecoded("/createUser", "CreateUserServlet");

        Tomcat.addServlet(ctx, "ListUsersServlet", new ListUsersServlet());
        ctx.addServletMappingDecoded("/listUsers", "ListUsersServlet");

        Tomcat.addServlet(ctx, "LoginServlet", new LoginServlet());
        ctx.addServletMappingDecoded("/login", "LoginServlet");

        Tomcat.addServlet(ctx, "LogoutServlet", new LogoutServlet());
        ctx.addServletMappingDecoded("/logout", "LogoutServlet");

        Tomcat.addServlet(ctx, "UpdateUserServlet", new UpdateUserServlet());
        ctx.addServletMappingDecoded("/updateUser", "UpdateUserServlet");

        Tomcat.addServlet(ctx, "UpdatePostServlet", new UpdatePostServlet());
        ctx.addServletMappingDecoded("/updatePost", "UpdatePostServlet");

        Tomcat.addServlet(ctx, "UserPostsServlet", new UserPostsServlet());
        ctx.addServletMappingDecoded("/index", "UserPostsServlet");

        Tomcat.addServlet(ctx, "DeletePostServlet", new DeletePostServlet());
        ctx.addServletMappingDecoded("/deletePost", "DeletePostServlet");

        Tomcat.addServlet(ctx, "DeleteUserServlet", new DeleteUserServlet());
        ctx.addServletMappingDecoded("/deleteUser", "DeleteUserServlet");
    }
}
