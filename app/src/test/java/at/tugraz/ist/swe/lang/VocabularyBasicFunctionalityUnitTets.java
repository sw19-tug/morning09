package at.tugraz.ist.swe.lang;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import android.content.Context;

import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

@RunWith(MockitoJUnitRunner.class)
public class VocabularyBasicFunctionalityUnitTets {

    Vocabulary vocabulary;

    @Mock
    Context mockContext;


    @Before
    public void setUp() {
        vocabulary = new Vocabulary(mockContext);
        vocabulary.add("Haus", "House");
    }

    @Test
    public void VocabularyStoreTest() {
        vocabulary.storeFile();
        File file = new File(mockContext.getFilesDir(), "vocabulary.json");
        assert(file.exists());
    }

    @Test
    public void VocabularyAddEntryTest() {
        assertNotEquals(0, vocabulary.add("Haus", "House"));
    }

    @Test
    public void VocabularyFindByNameTest() {
        assertNotEquals(0, vocabulary.findVocuabularyByName("Haus"));
    }

    @Test
    public void VocabularyRemoveEntryTest() {
        assertNotEquals(false, vocabulary.RemoveByName("Haus"));

    }
}
