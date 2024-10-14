package com.example.devSync.webService;


import com.example.devSync.bean.Utilisateur;
import com.example.devSync.bean.enums.Role;
import com.example.devSync.service.TaskHistoryService;
import com.example.devSync.service.TaskService;
import com.example.devSync.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/aboutMe")

public class AboutWebService extends HttpServlet {
    private UtilisateurService utilisateurService;
    private TaskService taskService;
    private TaskHistoryService taskHistoryService;

    @Override
    public void init() throws ServletException {
        utilisateurService = new UtilisateurService();
        taskHistoryService=new TaskHistoryService();
        taskService=new TaskService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur currentUser = (Utilisateur) session.getAttribute("currentUser");

        if (currentUser != null) {
            if (currentUser.getRole() == Role.MANAGER) {
                request.setAttribute("currentUser", currentUser);
                request.getRequestDispatcher("/views/aboutMe.jsp").forward(request, response);

            } else if (currentUser.getRole() == Role.USER) {

                request.setAttribute("currentUser", currentUser);
                request.getRequestDispatcher("/views/aboutMe.jsp").forward(request, response);

            }
        }
        else {
            response.sendRedirect("/login");
        }
    }

}
