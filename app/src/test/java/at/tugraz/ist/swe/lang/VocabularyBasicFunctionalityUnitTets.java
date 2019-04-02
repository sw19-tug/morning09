package at.tugraz.ist.swe.lang;

import org.junit.Test;
import static org.junit.Assert.*;

public class VocabularyBasicFunctionalityUnitTets {

    Vocabulary vocabulary = new Vocabulary();

    @Test
    public void VocabularyAddEntryTest() {
        assertNotEquals(0, vocabulary.Add("Haus"));
    }

    @Test
    public void VocabularyFindByNameTest() {
        assertNotEquals(0, vocabulary.findVocuabularyByName("Haus"));
    }

    @Test
    public void VocabularyFindByIdTest() {
        assertNotEquals(null, vocabulary.findVocuabularyByID(1));
    }

    @Test
    public void VocabularyRemoveEntryTest() {
        assertNotEquals(false, vocabulary.RemoveByName("Haus"));

    }

    @Test
    public void VocabularyLoadJsonTest() {
        assertNotEquals(false, vocabulary.Load());
    }

    @Test
    public void VocabularyStoreJsonTest() {
        assertNotEquals(false, vocabulary.Store());
    }

}
