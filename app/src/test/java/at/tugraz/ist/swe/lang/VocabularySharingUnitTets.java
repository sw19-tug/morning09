package at.tugraz.ist.swe.lang;

import android.content.Context;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VocabularySharingUnitTets {

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
    File testExportFile;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        when(mockContext.getFilesDir()).thenReturn(mTempFolder.newFolder());

        vocabulary = new Vocabulary(mockContext);
        file = vocabulary.file_;

        fileOutputStream = new FileOutputStream(file);
        fileInputStream = new FileInputStream(file);


//        when(mockContext.openFileOutput("vocabulary.json", Context.MODE_PRIVATE)).thenReturn(fileOutputStream);
        when(mockContext.openFileInput("vocabulary.json")).thenReturn(fileInputStream);

        CreateTestFiles("vocabularySharing_test.json");


//        when(mockContext.openFileOutput("vocabularySharing_test.json", Context.MODE_PRIVATE)).thenReturn(fileOutputStream);
        when(mockContext.openFileInput("vocabularySharing_test.json")).thenReturn(fileInputStream);
    }

    public void CreateTestFiles(String filename) {
        String filename_ = filename;
        testExportFile = new File(mockContext.getFilesDir(), filename_);
        if (!testExportFile.exists()) {
            try {
                testExportFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   /* @Test
    public void VocabularyStoreTest() {
        vocabulary.init();
        vocabulary.storeFile();
        File file = new File(mockContext.getFilesDir(), "vocabulary.json");
        assert (file.exists());
    }*/

    @Test
    public void VocabularyAddEntryTest() {

        vocabulary.init();
        assertNotEquals(-1, vocabulary.add("Haus_Export", "House_Exported"));
    }


    @Test
    public void VocabularySharingExportTest() {
        // ToDO: Export the entered Vocabulary here
        vocabulary.init();
        vocabulary.add("Haus_geteilt", "House_Shared");
        JSONObject myVoc  = vocabulary.getByName("Haus_geteilt");

        File export_file = vocabulary.getFileByName("vocabularySharing_test.json");
        //vocabulary.storeFileByName("vocabularySharing_test.json");
        vocabulary.exportVocabularyToFile(myVoc,export_file);
        assert (testExportFile.exists());


        assertNotEquals(false, vocabulary.removeByName("Haus_geteilt"));
        vocabulary.removeByName("Haus_geteilt");
        // Test Import Here
        VocabularySharingImportTest(export_file);
    }


    public void VocabularySharingImportTest(File FileNameToImport) {
        // ToDO: Clear the List before and then Import the entered Vocabulary here
        //vocabulary.init();
        //String filename_ = "vocabularySharing_test.json";
        //File export_file = vocabulary.getFileByName(filename_);
        vocabulary.importVocabularyTFromFile(FileNameToImport);
        assert (file.exists());

        assertNotEquals(-1, vocabulary.findByName("Haus_geteilt"));


    }

    @Test
    public void VocabularyLoadByName() {
        String filename = "vocabularySharing_test.json";
        assert(vocabulary.loadFileByName(filename));
        assert(vocabulary.file_.getName().equals(filename));
    }

}