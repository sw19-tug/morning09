package at.tugraz.ist.swe.lang;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

import android.content.Context;
import android.util.Log;

import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(MockitoJUnitRunner.class)
public class VocabularyBasicFunctionalityUnitTets {

    Vocabulary vocabulary;

    @Rule
    public TemporaryFolder mTempFolder = new TemporaryFolder();

    @Mock
    Context mockContext;

    @Mock
    FileInputStream fileInputStream;

    @Mock
    FileOutputStream fileOutputStream;

    File file;


    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        when(mockContext.getFilesDir()).thenReturn(mTempFolder.newFolder());

        vocabulary = new Vocabulary(mockContext);
        file = vocabulary.file_;

        fileOutputStream = new FileOutputStream(file);
        fileInputStream = new FileInputStream(file);

        when(mockContext.openFileOutput("vocabulary.json", Context.MODE_PRIVATE)).thenReturn(fileOutputStream);
        when(mockContext.openFileInput("vocabulary.json")).thenReturn(fileInputStream);

    }

    @Test
    public void VocabularyStoreTest() {
        vocabulary.init();
        vocabulary.storeFile();
        File file = new File(mockContext.getFilesDir(), "vocabulary.json");
        assert (file.exists());
    }

    @Test
    public void VocabularyAddEntryTest() {

        vocabulary.init();
        assertNotEquals(-1, vocabulary.add("Haus", "House"));
    }

    @Test
    public void VocabularyFindByNameTest() {

        vocabulary.init();
        vocabulary.add("Haus", "House");
        assertNotEquals(-1, vocabulary.findByName("Haus"));
    }

    @Test
    public void VocabularyRemoveEntryTest() {

        vocabulary.init();
        vocabulary.add("Haus", "House");
        assertNotEquals(false, vocabulary.removeByName("Haus"));

    }
}