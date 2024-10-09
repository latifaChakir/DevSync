package com.example.devSync.service;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UtilisateurDao;
import com.example.devSync.dao.impl.UtilisateurDaoImpl;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;
import java.util.Optional;

public class UtilisateurService {
    private UtilisateurDao utilisateurDAO = new UtilisateurDaoImpl();

    public void createUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.save(utilisateur);
    }

    public Optional<Utilisateur> getUtilisateur(Long id) {
        return utilisateurDAO.findById(id);
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.findAll();
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        utilisateurDAO.update(utilisateur);
    }

    public void deleteUtilisateur(long id) {
        utilisateurDAO.delete(id);
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }
    public Utilisateur login(String username, String password) {
        Utilisateur u= utilisateurDAO.findByUsername(username);
            if (u !=null && BCrypt.checkpw(password, u.getMotDePasse())) {
                return u;
            }
        return null;
    }


}
