package com.example.devSync;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UtilisateurDao;
import com.example.devSync.dao.impl.UtilisateurDaoImpl;
import com.example.devSync.service.UtilisateurService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
UtilisateurService utilisateurService=new UtilisateurService();
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        for (Utilisateur u : utilisateurs) {
            System.out.println("Utilisateur: " + u.getNomUtilisateur() + ", Email: " + u.getEmail());
        }
    }
}
