package com.example.devSync.service;

import com.example.devSync.bean.Tag;
import com.example.devSync.dao.TagDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class TagServiceTest {
    @Mock
    private TagDao tagDaoMock;
    @InjectMocks
    private TagService tagServiceUnderTest;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testAddTag() {
        Tag tag = new Tag();
        tag.setName("sport");
        when(tagDaoMock.save(tag)).thenAnswer(invocation -> {
            Tag savedTag = invocation.getArgument(0);
            savedTag.setId(1L);
            return savedTag;
        });
        tagServiceUnderTest.addTag(tag);
        assertNotNull(tag, "The tag object should not be null");
        assertNotNull(tag.getId(), "L'ID de tag ne doit pas être null après la sauvegarde");
        Mockito.verify(tagDaoMock, times(1)).save(tag);
    }

    @Test
    public void testUpdateTag() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("education");
        when(tagDaoMock.update(tag)).thenAnswer(invocation -> invocation.getArgument(0));
        tagServiceUnderTest.updateTag(tag);
        Mockito.verify(tagDaoMock, times(1)).update(tag);
        assertEquals("education", tag.getName(), "Le nom du tag doit être 'education'");
    }

    @Test
    public void testGetTagById() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("sport");
        when(tagDaoMock.findById(1L)).thenReturn(Optional.of(tag));
        Optional<Tag> foundTag = tagServiceUnderTest.findTagById(1L);
        assertTrue(foundTag.isPresent(), "Le tag doit être présent");
        assertEquals(tag, foundTag.get(), "Le tag trouvé doit être égal au tag passé en argument");
        Mockito.verify(tagDaoMock, times(1)).findById(1L);
    }

    @Test
    public void testDeleteTag() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("sport");
        when(tagDaoMock.findById(1L)).thenReturn(Optional.of(tag));
        tagServiceUnderTest.deleteTag(1L);
        Mockito.verify(tagDaoMock, times(1)).delete(1L);

    }
    @Test
    public void testGetAllTags() {
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("sport");
        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("loisir");
        when(tagDaoMock.findAll()).thenReturn(List.of(tag1, tag2));
        List<Tag> allTags = tagServiceUnderTest.getAllTags();
        assertTrue(allTags.contains(tag1), "La liste des tags doit contenir le tag 1");
        assertTrue(allTags.contains(tag2), "La liste des tags doit contenir le tag 2");
        Mockito.verify(tagDaoMock, times(1)).findAll();

    }
}
