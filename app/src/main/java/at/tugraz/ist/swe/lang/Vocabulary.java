package at.tugraz.ist.swe.lang;


import android.util.Log;

import org.json.JSONArray;


public class Vocabulary {


    private String vocabularyId;
    private String vocabularyName;
    private static String FullFileName;

    public static final String ERROR = Vocabulary.class.getSimpleName();
    public static final String INFO = Vocabulary.class.getSimpleName();
    public static final String DEBUG = Vocabulary.class.getSimpleName();


    public Vocabulary() {

    }

    /**
     * Returns the JSON Object of the FullFileName
     * @return
     */
    public JSONArray Load()
    {
        //ToDO Find and Open File

        JSONArray currentArray = new JSONArray();
        // ToDO Return Found File as JSON ARRAY Object, or null

        Log.d(INFO, "Loading Successful \n");
        return currentArray;
    }

    public int Add (String vocabularyName )
    {
        boolean addSuccessFul = true;
        int vocabularyId = 0 ;

        // ToDO Save here.

        if (addSuccessFul == true)
        {
            return vocabularyId;
        }
        return 0;
    }

    /**
     * return ID of enterend string, or -1 when not found
     * @param vocabularyName
     * @return
     */
    public int findVocuabularyByName(String vocabularyName)
    {

        int vocabularyId = 0 ;

        return  vocabularyId;
    }

    /**
     * returns Vocabulary as Object by entered ID, or null if not found
     * @param vocabularyID
     * @return
     */
    public Vocabulary findVocuabularyByID(int vocabularyID)
    {
        //ToDo Check Vocabulary not null
        int vocabularyId = vocabularyID ;

        Vocabulary foundVocabulary = new Vocabulary();

        // ToDo Determine Vocabulary and prepare to return

        return  foundVocabulary;
    }

    public boolean RemoveByName (String vocabularyName )
    {
        boolean removeSuccessFul = true;
        int vocabularyId = 0 ;

        // ToDO Search And Delete here.

        return removeSuccessFul;
    }

    public String getVocabularyId()
    {
        return vocabularyId;
    }

    public String getVocabularyName()
    {
        return vocabularyName;
    }
    public void setVocabularyId(String vocabularyId)
    {
        this.vocabularyId = vocabularyId;
    }

    public void setVocabularyName (String vocabularyName)
    {
        this.vocabularyName = vocabularyName;
    }
}