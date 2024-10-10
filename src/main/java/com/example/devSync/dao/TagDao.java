package com.example.devSync.dao;

import com.example.devSync.bean.Tag;
import com.example.devSync.bean.enums.Status;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    public Tag save(Tag tag);
    public Optional<Tag> findById(Long id);
    public List<Tag> findAll();
    public Tag update(Tag tag);
    public void delete(long id);
    public List<Tag> findByName(String name);
}
