package at.tugraz.ist.swe.lang;


import android.util.Log;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;


public class Vocabulary {


    private String vocabularyId;
    private String vocabularyName;
    private static String FullFileName;
    private Map<String,String> vocabularyMap;

    public static final String ERROR = Vocabulary.class.getSimpleName();
    public static final String INFO = Vocabulary.class.getSimpleName();
    public static final String DEBUG = Vocabulary.class.getSimpleName();


    public Vocabulary() {
        vocabularyMap = new HashMap<String, String>();

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

        //Log.d(INFO, "Loading Successful \n");
        return currentArray;
    }

    public int Add (String vocabularyName )
    {
        vocabularyMap.put(vocabularyName, vocabularyName);


        boolean addSuccessFul = vocabularyMap.containsKey(vocabularyName);
        //int vocabularyId = 0 ; // TODO change return

        if (addSuccessFul == true)
        {
            return 1;
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

        boolean addSuccessFul = vocabularyMap.containsKey(vocabularyName);
        if (addSuccessFul) {
            vocabularyId = 1;
        }
        return  vocabularyId;
    }

    /**
     * returns Vocabulary as Object by entered ID, or null if not found
     * @param vocabularyID
     * @return
     */
    public Vocabulary findVocuabularyByID(int vocabularyID)
    {
        // TODO wait for DB implementatoin..
        return null;
        //ToDo Check Vocabulary not null
        //int vocabularyId = vocabularyID ;

        //Vocabulary foundVocabulary = new Vocabulary();

        // ToDo Determine Vocabulary and prepare to return

        //return  foundVocabulary;
    }

    public boolean RemoveByName (String vocabularyName )
    {
        boolean removeSuccessFul = true;
        vocabularyMap.remove(vocabularyName);

        removeSuccessFul = vocabularyMap.containsKey(vocabularyName);

        return !removeSuccessFul;
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

    public Object Store() {
        //TODO wait for JSON or DB implemtation...
        return null;
    }
}