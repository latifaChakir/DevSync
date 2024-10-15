package com.example.devSync.service;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UtilisateurDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UtilisateurServiceTest {

    @Mock
    private UtilisateurDao utilisateurDaoMock;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUtilisateurWithAssert() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("John");
        utilisateur.setPrenom("Doe");
        when(utilisateurDaoMock.save(utilisateur)).thenAnswer(invocation -> {
            Utilisateur savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        utilisateurService.createUtilisateur(utilisateur);

        assertNotNull(utilisateur.getId(), "L'ID de l'utilisateur ne doit pas être null après la sauvegarde");
        verify(utilisateurDaoMock).save(utilisateur);
    }
    @Test
    void testUpdateUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setNom("CHAKIR");
        utilisateur.setPrenom("Doe");
        utilisateurService.updateUtilisateur(utilisateur);
        verify(utilisateurDaoMock).update(utilisateur);
        assertEquals("CHAKIR", utilisateur.getNom(), "Le nom de l'utilisateur doit être 'John'");
        assertEquals("Doe", utilisateur.getPrenom(), "Le prénom de l'utilisateur doit être 'Doe'");
    }

}
