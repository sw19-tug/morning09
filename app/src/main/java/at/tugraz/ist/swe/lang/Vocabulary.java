package at.tugraz.ist.swe.lang;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import java.util.ArrayList;


public class Vocabulary {


    private Context context_;
    public File file_;
    private JSONArray vocabArray_;
    JSONObject vocabulary_;

    static String filename_ = "vocabulary.json";

    public Vocabulary(Context context) {
        context_ = context;

        //check if file exists
        file_ = new File(context.getFilesDir(), filename_);
        if (!file_.exists()) {
            try {
                file_.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetVocab() {
        deleteVocab();
        //check if file exists
        file_ = new File(context_.getFilesDir(), filename_);
        if (!file_.exists()) {
            try {
                file_.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        init();
    }

    /**
     * init function, reads array from file or creates a new one
     */
    public void init()
    {
        //convert to json
        try {
            //for unit tests, we probably need to restructure this
            String data = readFromFile();

            if(data.length() > 0){
                vocabulary_ = new JSONObject(data);
                vocabArray_ = vocabulary_.getJSONArray("vocabulary");
            } else {
                vocabulary_ = new JSONObject();
                vocabArray_ = new JSONArray();
                vocabulary_.put("vocabulary",vocabArray_);
            }

        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a new Entry, first is german and second engilish word
     * @param german
     * @param english
     * @return 0 on Success or -1 on Failure
     */
    public int add(String german, String english)
    {
        try{
            JSONObject newJsonObj = new JSONObject();

            newJsonObj.put("english", english);
            newJsonObj.put("german", german);
            newJsonObj.put("rating", 2);


            vocabArray_.put(newJsonObj);

            vocabulary_.put("vocabulary",vocabArray_);
        } catch(JSONException e){
            e.printStackTrace();
            return -1;
        }

        return 0;
    }



    /**
     * return ID of enterend string, or -1 when not found
     * @param vocabularyName
     * @return i
     */
    public int findByName(String vocabularyName)
    {
        try {
            for(int i=0; i < vocabArray_.length();i++){
                JSONObject entry = vocabArray_.getJSONObject(i);

                if(entry.getString("german").equals(vocabularyName))
                {
                    return i;
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return -1;
    }



    public JSONObject getByName(String vocabularyName)
    {
        try {
            for(int i=0; i < vocabArray_.length();i++){
                JSONObject entry = vocabArray_.getJSONObject(i);

                if(entry.getString("german").equals(vocabularyName))
                {
                    return entry;

                }

            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }


    public void exportToVocabulary(Vocabulary vocToExport, String FileName){
        getByName(FileName);


    }

    /**
     * get translation of a word, lang specifies return language
     * @param lang
     * @param word
     * @return
     */
    public String getTranslation(String lang, String word)
    {

        try {
            for(int i=0; i < vocabArray_.length();i++){
                JSONObject entry = vocabArray_.getJSONObject(i);

                if(lang.compareTo("english") == 0)
                {
                    if(entry.getString("german").equals(word))
                    {
                        return entry.getString("english");
                    }
                }

                if(lang.compareTo("german") == 0)
                {
                    if(entry.getString("english").equals(word))
                    {
                        return entry.getString("german");
                    }
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * removes Entry from Vocabulary
     * @param vocabularyName
     * @return true if removed, false if not
     */
    public boolean removeByName(String vocabularyName)
    {
        boolean found = false;
        int i = -1;

        try {
            for(i=0; i < vocabArray_.length();i++){
                JSONObject entry = vocabArray_.getJSONObject(i);

                if(entry.getString("german") == vocabularyName)
                {
                    found = true;
                    break;
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        if(found) {
            vocabArray_.remove(i);
            return true;
        }

        return false;
    }

    /**
     * Stores Json into File
     */
    public void storeFile() {

        String output = vocabulary_.toString();
        System.out.println(output);
        try{
            FileOutputStream fos = context_.openFileOutput(filename_, Context.MODE_PRIVATE);
            fos.write(output.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportVocabularyToFile(JSONObject vocToExport, File FileName)
    {
        String output = vocToExport.toString();
        System.out.println(output);
        file_ = FileName;
        if (!file_.exists()) {
            try {
                file_.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{

            FileOutputStream fos1 = new FileOutputStream(file_);
            fos1.write(output.getBytes());
            fos1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //return 0;
    }

    public void importVocabularyTFromFile(File FileName)
    {
         String readString = null;
        try {
            FileInputStream is = new FileInputStream(FileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            readString = new String(buffer, "UTF-8");


            JSONObject myImportedVoc = new JSONObject(readString);

            if(findByName(myImportedVoc.get("german").toString()) > 0)
            {
                // Vocabulary found -> do not insert again
                // ToDo: put popup here

                return ;
            }
            else
            {
                // Vocabulary not found -> Call Add Method
                add(myImportedVoc.get("german").toString(), myImportedVoc.get("english").toString());
            }




        } catch(IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e1)
        {
            e1.printStackTrace();
        }

        return;
    }

    /**
     * Reads content (Json) from file
     * @return returnString
     */
    public String readFromFile() {

        String readString = null;
        try {
            FileInputStream is = context_.openFileInput(filename_);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            readString = new String(buffer, "UTF-8");
        } catch(IOException e){
            e.printStackTrace();
        }

        return readString;
    }

    /**
     * Get Vocabulary Json Array
     * @return JsonArray
     */
    public JSONArray getVocabArray() {
        return vocabArray_;
    }

    /**
     * Store to file by filename
     * @param filename
     */
    public void storeFileByName(String filename) {

        String output = vocabulary_.toString();
        System.out.println(output);
        file_ = new File(context_.getFilesDir(), filename);
        if (!file_.exists()) {
            try {
                file_.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            FileOutputStream fos = context_.openFileOutput(filename_, Context.MODE_PRIVATE);
            fos.write(output.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load a different vocabulary from a file
     * @param filename
     * @return
     */
    public boolean loadFileByName(String filename) {

        filename_ = filename;

        //check if file exists
        file_ = new File(context_.getFilesDir(), filename_);
        if (!file_.exists()) {
            return false;
        }

        init();
        return true;
    }

    public File getFileByName(String filename) {

        filename_ = filename;

        //check if file exists
        file_ = new File(context_.getFilesDir(), filename_);
        if (!file_.exists()) {
            return null;
        }

        //init();
        return file_;
    }


    public void deleteVocab() {
        File deleteFile = new File(context_.getFilesDir(), filename_);
        deleteFile.delete();
    }

    public void addRating(int id, float rating) {
        try {
            JSONObject entry = vocabArray_.getJSONObject(id);
            entry.put("rating", rating);
            storeFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addTags(int objectId, ArrayList<String> tagsArray) {

        try {
            JSONArray array = new JSONArray();
            for (int i=0; i<tagsArray.size(); i++) {
                array.put(tagsArray.get(i));
            }
            JSONObject entry = vocabArray_.getJSONObject(objectId);
            entry.put("tags", array);
            storeFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void removeTag(int objectId, String tagName) {

        int i = -1;

        try {

            JSONObject entry = vocabArray_.getJSONObject(objectId);
            JSONArray tagsArray = entry.getJSONArray("tags");
            entry.remove("tags");
            for(i=0; i < tagsArray.length();i++){
                String actualTag = tagsArray.getString(i);

                if(actualTag.equals(tagName))
                {
                    tagsArray.remove(i);
                    entry.put("tags", tagsArray);
                }
            }
            storeFile();
        } catch(JSONException e){
            e.printStackTrace();
        }

    }
}