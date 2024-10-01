package com.example.devSync.dao;

import com.example.devSync.bean.Utilisateur;
import java.util.List;
import java.util.Optional;

public interface UtilisateurDao {
    public Utilisateur save(Utilisateur utilisateur);
    public Optional<Utilisateur> findById(int id);
    public List<Utilisateur> findAll();
    public Utilisateur update(Utilisateur utilisateur);
    public void delete(int id);
    public List<Utilisateur> findByName(String name);
}
