package com.example.devSync.service;

import com.example.devSync.bean.UserToken;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UserTokenDao;
import com.example.devSync.dao.impl.UserTokenDaoImpl;

import java.util.List;
import java.util.Optional;

public class UserTokenService {
    private UserTokenDao userTokenDao;
    UtilisateurService utilisateurService;
    public UserTokenService() {
        this.userTokenDao=new UserTokenDaoImpl();
        this.utilisateurService=new UtilisateurService();
    }
    public UserTokenService(UserTokenDao userTokenDao, UtilisateurService utilisateurService) {
        this.userTokenDao = userTokenDao;
        this.utilisateurService = utilisateurService;
    }

    public void save(UserToken token) {
        userTokenDao.save(token);
    }
    public Optional<UserToken> findByToken(long token) {
        return userTokenDao.findById(token);
    }
    public List<UserToken> findByTokenType(String tokenType) {
       return (List<UserToken>) userTokenDao.findByTokenType(tokenType);
    }
    public void update(UserToken token){
        userTokenDao.update(token);
    }
    public UserToken findByUserAndTokenType(Utilisateur user, String tokenType) {
        return userTokenDao.findByUserAndType(user.getId(), tokenType);
    }


}
