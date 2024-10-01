package com.example.devSync.webService;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/utilisateurs")

public class UtilisateurWebService extends HttpServlet {
    UtilisateurService utilisateurService=new UtilisateurService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Utilisateur> utilisateursList = utilisateurService.getAllUtilisateurs();
            if (utilisateursList == null || utilisateursList.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé.");
            } else {
                System.out.println("Utilisateurs trouvés : " + utilisateursList.size());
            }
            request.setAttribute("utilisateursList", utilisateursList);
            request.getRequestDispatcher("/utilisateurs.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Une erreur s'est produite : " + e.getMessage());
        }
    }
}
