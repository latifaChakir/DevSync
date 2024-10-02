package com.example.devSync.webService;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.service.UtilisateurService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String prenom = request.getParameter("prenom");
            String nom = request.getParameter("nom");
            String email = request.getParameter("email");
            String nomUtilisateur = request.getParameter("nom_utilisateur");
            String motDePasse = request.getParameter("mot_de_passe");
            long managerId = Integer.parseInt(request.getParameter("manager_id"));

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setPrenom(prenom);
            utilisateur.setNom(nom);
            utilisateur.setEmail(email);
            utilisateur.setNomUtilisateur(nomUtilisateur);
            utilisateur.setMotDePasse(motDePasse);
            Utilisateur manager = utilisateurService.getUtilisateur(managerId).orElse(null);
            utilisateur.setManager(manager);
            utilisateurService.createUtilisateur(utilisateur);

            response.sendRedirect(request.getContextPath() + "/utilisateurs");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Une erreur s'est produite : " + e.getMessage());
        }
    }
}
