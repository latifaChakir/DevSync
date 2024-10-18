package com.example.devSync.service;

import com.example.devSync.bean.Utilisateur;
import com.example.devSync.bean.enums.Role;
import com.example.devSync.dao.UtilisateurDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void testCreateUtilisateurWithAllAttributes() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomUtilisateur("johnDoe");
        utilisateur.setMotDePasse("securePassword123");
        utilisateur.setNom("John");
        utilisateur.setPrenom("Doe");
        utilisateur.setEmail("john.doe@example.com");
        utilisateur.setRole(Role.MANAGER);

        when(utilisateurDaoMock.save(utilisateur)).thenAnswer(invocation -> {
            Utilisateur savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        utilisateurService.createUtilisateur(utilisateur);
        assertNotNull(utilisateur.getId(), "L'ID de l'utilisateur ne doit pas être null après la sauvegarde");
        verify(utilisateurDaoMock, times(1)).save(utilisateur);
        assertEquals("johnDoe", utilisateur.getNomUtilisateur(), "Le nom d'utilisateur doit être 'johnDoe'");
        assertEquals("John", utilisateur.getNom(), "Le nom doit être 'John'");
        assertEquals("Doe", utilisateur.getPrenom(), "Le prénom doit être 'Doe'");
        assertEquals("john.doe@example.com", utilisateur.getEmail(), "L'email doit être 'john.doe@example.com'");
        assertEquals(Role.MANAGER, utilisateur.getRole(), "Le rôle doit être MANAGER");
    }

    @Test
    void testUpdateUtilisateurWithAllAttributes() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setNomUtilisateur("johnUpdated");
        utilisateur.setMotDePasse("newPassword456");
        utilisateur.setNom("John");
        utilisateur.setPrenom("Doe");
        utilisateur.setEmail("john.updated@example.com");
        utilisateur.setRole(Role.USER);
        utilisateurService.updateUtilisateur(utilisateur);

        verify(utilisateurDaoMock, times(1)).update(utilisateur);
        assertEquals("johnUpdated", utilisateur.getNomUtilisateur(), "Le nom d'utilisateur doit être 'johnUpdated'");
        assertEquals("newPassword456", utilisateur.getMotDePasse(), "Le mot de passe doit être 'newPassword456'");
        assertEquals("John", utilisateur.getNom(), "Le nom doit être 'John'");
        assertEquals("Doe", utilisateur.getPrenom(), "Le prénom doit être 'Doe'");
        assertEquals("john.updated@example.com", utilisateur.getEmail(), "L'email doit être 'john.updated@example.com'");
        assertEquals(Role.USER, utilisateur.getRole(), "Le rôle doit être USER");
    }

    @Test
    void testDeleteUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateurService.deleteUtilisateur(utilisateur.getId());
        verify(utilisateurDaoMock, times(1)).delete(1L);
    }

    @Test
    void testGetUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setNomUtilisateur("johnDoe");
        utilisateur.setMotDePasse("securePassword123");
        utilisateur.setNom("John");
        utilisateur.setPrenom("Doe");
        utilisateur.setEmail("john.doe@example.com");
        utilisateur.setRole(Role.MANAGER);

        when(utilisateurDaoMock.findById(1L)).thenReturn(java.util.Optional.of(utilisateur));
        Utilisateur result = utilisateurService.getUtilisateur(1L).orElse(null);
        assertEquals(utilisateur, result, "L'utilisateur retourné doit être le même que celui attendu");
        verify(utilisateurDaoMock, times(1)).findById(1L);
    }

    @Test
    void testGetAllUtilisateurs() {
        when(utilisateurDaoMock.findAll()).thenReturn(java.util.Arrays.asList(new Utilisateur(), new Utilisateur()));
        var utilisateurs = utilisateurService.getAllUtilisateurs();
        assertEquals(2, utilisateurs.size(), "Le nombre d'utilisateurs retournés doit être 2");
        verify(utilisateurDaoMock, times(1)).findAll();
    }

    @Test
    void testHashPassword(){
        String password ="securePassword123";
        String hashedPassword = utilisateurService.hashPassword(password);
        assertNotEquals(password, hashedPassword);
        assertTrue(BCrypt.checkpw(password, hashedPassword), "Le mot de passe haché doit correspondre au mot de passe en texte clair");
    }

    @Test
    void testLogin_Success() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setNomUtilisateur("johnDoe");
        String plainPassword = "securePassword123";
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        utilisateur.setMotDePasse(hashedPassword);
        when(utilisateurDaoMock.findByUsername("johnDoe")).thenReturn(utilisateur);
        Utilisateur result = utilisateurService.login("johnDoe", plainPassword);
        assertEquals(utilisateur, result, "L'utilisateur retourné doit être le même que celui attendu");
        verify(utilisateurDaoMock, times(1)).findByUsername("johnDoe");
    }

    @Test
    void testLogin_UserNotFound() {
        when(utilisateurDaoMock.findByUsername("nonExistentUser")).thenReturn(null);
        Utilisateur result = utilisateurService.login("nonExistentUser", "anyPassword");
        assertNull(result, "L'utilisateur doit être null lorsque le nom d'utilisateur n'existe pas");
        verify(utilisateurDaoMock, times(1)).findByUsername("nonExistentUser");
    }

    @Test
    void testLogin_IncorrectPassword() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setNomUtilisateur("johnDoe");
        String plainPassword = "securePassword123";
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        utilisateur.setMotDePasse(hashedPassword);
        when(utilisateurDaoMock.findByUsername("johnDoe")).thenReturn(utilisateur);
        Utilisateur result = utilisateurService.login("johnDoe", "wrongPassword");
        assertNull(result, "L'utilisateur doit être null lorsque le mot de passe est incorrect");
        verify(utilisateurDaoMock, times(1)).findByUsername("johnDoe");
    }

}
