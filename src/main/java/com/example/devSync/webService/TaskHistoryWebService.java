package com.example.devSync.webService;

import com.example.devSync.bean.Tag;
import com.example.devSync.bean.Task;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.service.TaskHistoryService;
import com.example.devSync.service.TaskService;
import com.example.devSync.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/taskHistory")
public class TaskHistoryWebService extends HttpServlet {
    private TaskHistoryService taskHistoryService=new TaskHistoryService();
    private TaskService taskService=new TaskService();
    private UtilisateurService utilisateurService=new UtilisateurService();
    @Override
    public void init() throws ServletException {
        System.out.println("Servlet initialisée.");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "remplace":
                Long taskId = Long.valueOf(request.getParameter("id"));
                Task task =taskService.getTaskById(taskId);
                Long newUserId = Long.valueOf(request.getParameter("newUserId"));
                Utilisateur newUser = utilisateurService.getUtilisateur(newUserId).orElse(null);
                taskHistoryService.AskToRemplace(task, newUser);
                response.sendRedirect(request.getContextPath() + "/profil");
                break;
            case "delete":

            case "update":

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }


}
