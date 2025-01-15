package com.example.devSync.service;

import com.example.devSync.bean.UserToken;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UserTokenDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTokenServiceTest {

    @Mock
    private UserTokenDao userTokenDaoMock;
    @Mock
    private UtilisateurService userServiceMock;

    @InjectMocks
    private UserTokenService userTokenServiceUnderTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUserToken() {
        Utilisateur user = new Utilisateur();
        user.setId(1L);
        UserToken token = new UserToken();
        token.setId(1L);
        token.setUser(user);
        token.setTokenType("Remplacement");
        token.setTokenCount(2);
        token.setLastReset(LocalDate.now());
        userTokenServiceUnderTest.save(token);
        verify(userTokenDaoMock, times(1)).save(token);
    }

    @Test
    public void testFindByToken() {
        Utilisateur user = new Utilisateur();
        user.setId(1L);

        UserToken token = new UserToken();
        token.setId(1L);
        token.setUser(user);
        token.setTokenType("Remplacement");
        token.setTokenCount(2);
        token.setLastReset(LocalDate.now());

        when(userTokenDaoMock.findById(1L)).thenReturn(Optional.of(token));
        Optional<UserToken> foundToken = userTokenServiceUnderTest.findByToken(1L);

        assertTrue(foundToken.isPresent(), "Le token doit être présent");
        assertEquals(token, foundToken.get(), "Le token trouvé doit être égal au token passé en argument");
        verify(userTokenDaoMock, times(1)).findById(1L);
    }

    @Test
    public void testFindByTokenType() {
        Utilisateur user = new Utilisateur();
        user.setId(1L);

        UserToken token1 = new UserToken();
        token1.setId(1L);
        token1.setUser(user);
        token1.setTokenType("type1");
        token1.setTokenCount(2);
        token1.setLastReset(LocalDate.now());

        UserToken token2 = new UserToken();
        token2.setId(2L);
        token2.setUser(user);
        token2.setTokenType("type1");
        token2.setTokenCount(3);
        token2.setLastReset(LocalDate.now());

        when(userTokenDaoMock.findByTokenType("type1")).thenReturn(List.of(token1, token2));

        List<UserToken> tokens = userTokenServiceUnderTest.findByTokenType("type1");

        assertEquals(2, tokens.size(), "La liste doit contenir deux tokens");
        assertTrue(tokens.contains(token1), "La liste doit contenir token1");
        assertTrue(tokens.contains(token2), "La liste doit contenir token2");
        verify(userTokenDaoMock, times(1)).findByTokenType("type1");
    }

    @Test
    public void testUpdateUserToken() {
        Utilisateur user = new Utilisateur();
        user.setId(1L);

        UserToken token = new UserToken();
        token.setId(1L);
        token.setUser(user);
        token.setTokenType("updatedToken");
        token.setTokenCount(2);
        token.setLastReset(LocalDate.now());

        userTokenServiceUnderTest.update(token);
        verify(userTokenDaoMock, times(1)).update(token);
    }

    @Test
    public void testFindByUserAndTokenType() {
        Utilisateur user = new Utilisateur();
        user.setId(1L);

        UserToken token = new UserToken();
        token.setId(1L);
        token.setUser(user);
        token.setTokenType("Remplacement");
        token.setTokenCount(2);
        token.setLastReset(LocalDate.now());

        when(userTokenDaoMock.findByUserAndType(user.getId(), "type1")).thenReturn(token);
        UserToken foundToken = userTokenServiceUnderTest.findByUserAndTokenType(user, "type1");

        assertEquals(token, foundToken, "Le token trouvé doit être égal au token passé en argument");
        verify(userTokenDaoMock, times(1)).findByUserAndType(user.getId(), "type1");
    }
}
