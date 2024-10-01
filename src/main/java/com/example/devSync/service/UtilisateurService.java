package com.example.devSync.service;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UtilisateurDao;
import com.example.devSync.dao.impl.UtilisateurDaoImpl;

import java.util.List;

public class UtilisateurService {
    private UtilisateurDao utilisateurDAO = new UtilisateurDaoImpl();

    public void createUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.save(utilisateur);
    }

//    public Utilisateur getUtilisateur(Long id) {
//        return utilisateurDAO.findById(id);
//    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.findAll();
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.update(utilisateur);
    }

    public void deleteUtilisateur(int id) {
        utilisateurDAO.delete(id);
    }
}
