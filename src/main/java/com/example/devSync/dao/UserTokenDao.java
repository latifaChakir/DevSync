package com.example.devSync.dao;

import com.example.devSync.bean.UserToken;

import java.util.List;
import java.util.Optional;

public interface UserTokenDao {
    public UserToken save(UserToken userToken);
    public Optional<UserToken> findById(Long id);
    public List<UserToken> findAll();
    public UserToken update(UserToken userToken);
    public void delete(long id);
    public List<UserToken>findByTokenType(String tokenType);
    public UserToken findByUser(Long userId);
    public UserToken findByUserAndType(Long userId, String tokenType);
}
