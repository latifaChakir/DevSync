package com.example.devSync.webService;

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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class StatisticWebService extends HttpServlet {
    private TaskService taskService;
    @Override
    public void init() throws ServletException {
        taskService=new TaskService();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur currentUser = (Utilisateur) session.getAttribute("currentUser");
        Map<String, Long> statistics = taskService.calculateStatisticsByCreatorId(currentUser.getId());

        request.setAttribute("totalCreatedTasks", statistics.get("totalCreatedTasks"));
        request.setAttribute("completedCreatedTasks", statistics.get("completedCreatedTasks"));
        request.setAttribute("inProgressCreatedTasks", statistics.get("inProgressCreatedTasks"));
        request.setAttribute("notStartedCreatedTasks", statistics.get("notStartedCreatedTasks"));
        request.setAttribute("notEffectuedCreatedTasks", statistics.get("notEffectuedCreatedTasks"));

        request.getRequestDispatcher("/views/Dashboard.jsp").forward(request, response);
    }

}
