package com.example.devSync.bean;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_utilisateur",nullable = true, unique = true)
    private String nomUtilisateur;

    @Column(name = "mot_de_passe" ,nullable = false)
    private String motDePasse;
    @Column(nullable = false)
    private String nom;
    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Utilisateur manager;

    public Utilisateur(long l, String nom1, String email1) {
        this.id = l;
        this.nomUtilisateur = nom1;
        this.email = email1;
    }

    public Utilisateur() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Utilisateur getManager() {
        return manager;
    }

    public void setManager(Utilisateur manager) {
        this.manager = manager;
    }


}
