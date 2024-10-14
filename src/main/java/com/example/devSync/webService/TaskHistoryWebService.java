package com.example.devSync.webService;

import com.example.devSync.bean.Tag;
import com.example.devSync.bean.Task;
import com.example.devSync.bean.TaskHistory;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.service.TaskHistoryService;
import com.example.devSync.service.TaskService;
import com.example.devSync.service.UserTokenService;
import com.example.devSync.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/taskHistory")
public class TaskHistoryWebService extends HttpServlet {
    private TaskHistoryService taskHistoryService=new TaskHistoryService();
    private TaskService taskService=new TaskService();
    private UtilisateurService utilisateurService=new UtilisateurService();
    private UserTokenService userTokenService=new UserTokenService();
    @Override
    public void init() throws ServletException {
        System.out.println("Servlet initialisée.");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Long taskHId = Long.valueOf(request.getParameter("id"));

        switch (action) {
            case "remplace":
                Long taskId = Long.valueOf(request.getParameter("id"));
                Task task =taskService.getTaskById(taskId);
                Long newUserId = Long.valueOf(request.getParameter("newUserId"));
                Utilisateur utilisateur =utilisateurService.getUtilisateur(newUserId).orElse(null);
                int countTokenRempl=userTokenService.findByUserAndTokenType(utilisateur,"Remplacement").getTokenCount();
                if (countTokenRempl<1){
                    request.getSession().setAttribute("errorMessage", "Vous ne pouvez pas effectuer cette action, vous n'avez pas assez de jetons");
                    response.sendRedirect("/DevSync/profil");
                    return;
                }
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
                int countTokenSupp=userTokenService.findByUserAndTokenType(newUser1,"Suppression").getTokenCount();
                if (countTokenSupp < 1) {
                    request.getSession().setAttribute("errorMessage", "Vous ne pouvez pas effectuer cette action, vous n'avez pas assez de jetons");
                    response.sendRedirect("/DevSync/profil");
                    return;
                }


                taskHistoryService.AskToRemove(task1, newUser1);
                response.sendRedirect(request.getContextPath() + "/profil");
                break;


            case "approuver":
                TaskHistory taskHistory = taskHistoryService.getTaskHistoryById(taskHId);
                if (taskHistory == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Historique de tâche non trouvé");
                    break;
                }
                taskHistoryService.approveRemplace(taskHistory);
                long TaskID=taskHistory.getTask().getId();
                if(taskHistory.getIsApproved() && Objects.equals(taskHistory.getTypeModification(), "Remplacement")) {
                    response.sendRedirect(request.getContextPath() + "/tasks?id=" + TaskID + "&action=edit");
                    break;
                } else if (Objects.equals(taskHistory.getTypeModification(), "Suppression") && taskHistory.getIsApproved() ) {
                    taskService.deleteTask(taskHistory.getTask().getId());
                    response.sendRedirect(request.getContextPath() + "/profil");
                }

            case "refuser":
                TaskHistory taskHistory1 = taskHistoryService.getTaskHistoryById(taskHId);
                if (taskHistory1 == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Historique de tâche non trouvé");
                    break;
                }
                taskHistoryService.desapproveRemplace(taskHistory1);
                response.sendRedirect(request.getContextPath() + "/profil");
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }


}
