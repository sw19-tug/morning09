package at.tugraz.ist.swe.lang;

import java.util.HashMap;
import java.util.Map;


public class Vocabulary {


    private Map<String,String> vocabularyMap;

    public Vocabulary() {
        vocabularyMap = new HashMap<String, String>();

    }

    public int Add (String vocabularyName )
    {
        vocabularyMap.put(vocabularyName, vocabularyName);

        boolean addSuccessFul = vocabularyMap.containsKey(vocabularyName);

        return addSuccessFul ? 1 : 0;
    }

    /**
     * return ID of enterend string, or -1 when not found
     * @param vocabularyName
     * @return
     */
    public int findVocuabularyByName(String vocabularyName)
    {
        boolean addSuccessFul = vocabularyMap.containsKey(vocabularyName);

        return  addSuccessFul ? 1 : 0;
    }

    public boolean RemoveByName (String vocabularyName )
    {
        vocabularyMap.remove(vocabularyName);

        boolean removeSuccessFul = vocabularyMap.containsKey(vocabularyName);

        return !removeSuccessFul;
    }
}