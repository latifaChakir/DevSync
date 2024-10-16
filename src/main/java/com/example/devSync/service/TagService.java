package com.example.devSync.service;

import com.example.devSync.bean.Tag;
import com.example.devSync.dao.TagDao;
import com.example.devSync.dao.impl.TagDaoImpl;

import java.util.List;
import java.util.Optional;

public class TagService {
    private TagDao tagDao;
    public TagService() {
        this.tagDao = new TagDaoImpl();
    }
    public TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public List<Tag> getAllTags() {
        return tagDao.findAll();
    }

    public void deleteTag(long tagId) {
        tagDao.delete(tagId);
    }

    public void addTag(Tag tag) {
        tagDao.save(tag);
    }

    public Tag updateTag(Tag tag) {
        return tagDao.update(tag);
    }

    public Optional<Tag> findTagById(long id) {
        return tagDao.findById(id);
    }

}
