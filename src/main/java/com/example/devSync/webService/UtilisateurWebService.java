package com.example.devSync.webService;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.bean.enums.Role;
import com.example.devSync.service.UtilisateurService;
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
    public void init() throws ServletException {
        System.out.println("Servlet initialisée.");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("edit".equals(action)) {
                Long utilisateurId = Long.valueOf(request.getParameter("id"));
                Utilisateur utilisateur = utilisateurService.getUtilisateur(utilisateurId).orElse(null);
                if (utilisateur != null) {
                    request.setAttribute("utilisateur", utilisateur);
                    request.getRequestDispatcher("/views/editUtilisateur.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilisateur non trouvé");
                }
            } else {
                HttpSession session = request.getSession();
                Utilisateur currentUser = (Utilisateur) session.getAttribute("currentUser");
                List<Utilisateur> utilisateursList = utilisateurService.getAllUtilisateurs();
                request.setAttribute("currentUser", currentUser);
                request.setAttribute("utilisateursList", utilisateursList);
                request.getRequestDispatcher("/views/utilisateurs.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Une erreur s'est produite : " + e.getMessage());
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String action = req.getParameter("action");

        try {
            if ("add".equals(action)) {
                String prenom = req.getParameter("prenom");
                String nom = req.getParameter("nom");
                String email = req.getParameter("email");
                String nomUtilisateur = req.getParameter("nom_utilisateur");
                String motDePasse = req.getParameter("mot_de_passe");
                Role role = Role.valueOf(req.getParameter("role"));

                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setPrenom(prenom);
                utilisateur.setNom(nom);
                utilisateur.setEmail(email);
                utilisateur.setNomUtilisateur(nomUtilisateur);
                String hashedPassword = utilisateurService.hashPassword(motDePasse);
                utilisateur.setMotDePasse(hashedPassword);
                utilisateur.setRole(role);

                utilisateurService.createUtilisateur(utilisateur);

            } else if ("update".equals(action)) {
                Long userId = Long.valueOf(req.getParameter("id"));
                String prenom = req.getParameter("prenom");
                String nom = req.getParameter("nom");
                String email = req.getParameter("email");
                String nomUtilisateur = req.getParameter("nom_utilisateur");
                String motDePasse = req.getParameter("mot_de_passe");
                Role role = Role.valueOf(req.getParameter("role"));
                Utilisateur utilisateur = utilisateurService.getUtilisateur(userId).orElseThrow(() -> new Exception("Utilisateur non trouvé"));
                utilisateur.setPrenom(prenom);
                utilisateur.setNom(nom);
                utilisateur.setEmail(email);
                utilisateur.setNomUtilisateur(nomUtilisateur);
                if (motDePasse != null && !motDePasse.trim().isEmpty()) {
                    String hashedPassword = utilisateurService.hashPassword(motDePasse);
                    utilisateur.setMotDePasse(hashedPassword);
                }
                utilisateur.setRole(role);
                utilisateurService.updateUtilisateur(utilisateur);


            } else if ("delete".equals(action)) {
                Long userId = Long.valueOf(req.getParameter("id"));
                utilisateurService.deleteUtilisateur(userId);
            }
            res.sendRedirect(req.getContextPath() + "/utilisateurs");

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("Une erreur s'est produite : " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        System.out.println("Servlet détruite.");
    }
}
