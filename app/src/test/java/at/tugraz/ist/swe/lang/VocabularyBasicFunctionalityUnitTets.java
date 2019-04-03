package at.tugraz.ist.swe.lang;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VocabularyBasicFunctionalityUnitTets {

    Vocabulary vocabulary = new Vocabulary();

    @Before
    public void setUp() {
        vocabulary.Add("Haus");
    }

    @Test
    public void VocabularyAddEntryTest() {
        assertNotEquals(0, vocabulary.Add("Haus"));
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
