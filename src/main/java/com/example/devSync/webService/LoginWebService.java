package com.example.devSync.webService;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginWebService extends HttpServlet {
    private UtilisateurService utilisateurService;

    @Override
    public void init() throws ServletException {
        utilisateurService = new UtilisateurService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Supprime l'attribut loginError pour s'assurer qu'il n'y a pas d'erreur lors du chargement de la page
        request.removeAttribute("loginError");
        request.getRequestDispatcher("/views/signin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Utilisateur currentUtilisateur = utilisateurService.login(username, password);

        if (currentUtilisateur != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", currentUtilisateur);
            session.setAttribute("username", username);
            response.sendRedirect(request.getContextPath() + "/profil");
        } else {
            request.setAttribute("loginError", "Invalid username or password.");
            request.getRequestDispatcher("/views/signin.jsp").forward(request, response);
        }
    }
}

