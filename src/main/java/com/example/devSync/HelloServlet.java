package com.example.devSync;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.getWriter().write("<h1>welcome to youTask! Salam Latiiifa !!!</h1>");
        resp.getWriter().write("<form action=\"/DevSync/processForm\" method=\"POST\">\n" +
                "    <input type=\"text\" name=\"username\" />\n" +
                "    <input type=\"password\" name=\"password\" />\n" +
                "    <input type=\"submit\" value=\"Submit\" />\n" +
                "</form>\n");
    }
}
