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
            case "remove":
                Long Id = Long.valueOf(request.getParameter("id"));
                Task task1 = taskService.getTaskById(Id);

                if (task1 == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tâche non trouvée");
                    break;
                }

                Long newUserId1 = Long.valueOf(request.getParameter("newUserId"));
                Utilisateur newUser1 = utilisateurService.getUtilisateur(newUserId1).orElse(null);

                if (newUser1 == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilisateur non trouvé");
                    break;
                }

                taskHistoryService.AskToRemove(task1, newUser1);
                response.sendRedirect(request.getContextPath() + "/profil");
                break;


            case "update":

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }


}
