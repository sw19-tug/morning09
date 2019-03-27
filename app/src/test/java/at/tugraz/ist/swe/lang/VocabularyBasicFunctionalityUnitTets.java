package at.tugraz.ist.swe.lang;

import org.junit.Test;
import static org.junit.Assert.*;

public class VocabularyBasicFunctionalityUnitTets {

    Vocabulary vocabulary = new Vocabulary();

    @Test
    public void VocabularyAddEntryTest() {
        assertNotEquals(0, Vocabulary.Add("Haus"));
    }

    @Test
    public void VocabularyFindByNameTest() {
        assertNotEquals(0, Vocabulary.findVocuabularyByName("Haus"));
    }

    @Test
    public void VocabularyFindByIdTest() {
        assertNotEquals(null, Vocabulary.findVocuabularyByID(1));
    }

    @Test
    public void VocabularyRemoveEntryTest() {
        assertNotEquals(false, Vocabulary.RemoveByName("Haus"));

    }

    @Test
    public void VocabularyLoadJsonTest() {
        assertNotEquals(false, Vocabulary.Load());
    }

    @Test
    public void VocabularyStoreJsonTest() {
        assertNotEquals(false, Vocabulary.Store());
    }

}
