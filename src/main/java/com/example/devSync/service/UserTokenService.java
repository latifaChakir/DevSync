package com.example.devSync.service;

import com.example.devSync.bean.UserToken;
import com.example.devSync.bean.Utilisateur;
import com.example.devSync.dao.UserTokenDao;
import com.example.devSync.dao.impl.UserTokenDaoImpl;

import java.util.List;
import java.util.Optional;

public class UserTokenService {
    private UserTokenDao userTokenDao=new UserTokenDaoImpl();
    UtilisateurService utilisateurService=new UtilisateurService();
    public void save(UserToken token) {
        userTokenDao.save(token);
    }
    public Optional<UserToken> findByToken(long token) {
        return userTokenDao.findById(token);
    }
    public void deleteByToken(long token) {
        userTokenDao.delete(token);
    }
    public List<UserToken> findByTokenType(String tokenType) {
       return (List<UserToken>) userTokenDao.findByTokenType(tokenType);
    }
    public void update(UserToken token){
        userTokenDao.update(token);
    }
    public UserToken findByUser(Utilisateur user) {
        return userTokenDao.findByUser(user.getId());
    }
    public UserToken findByUserAndTokenType(Utilisateur user, String tokenType) {
        return userTokenDao.findByUserAndType(user.getId(), tokenType);
    }


}
