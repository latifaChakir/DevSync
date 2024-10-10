package com.example.devSync.service;

import com.example.devSync.bean.UserToken;
import com.example.devSync.dao.UserTokenDao;
import com.example.devSync.dao.impl.UserTokenDaoImpl;

import java.util.List;
import java.util.Optional;

public class UserTokenService {
    private UserTokenDao userTokenDao=new UserTokenDaoImpl();
    public void save(UserToken token) {
        System.out.println("hello");
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

}
