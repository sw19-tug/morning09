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


public class Vocabulary {


    private Context context_;
    public File file_;
    private JSONArray vocabArray_;
    JSONObject vocabulary_;
    private String filename_ = "vocabulary.json";
    public File FileName;


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

        if(findByName(german) != -1)
        {
            return 0;
        }

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
            //FileOutputStream fos = context_.openFileOutput(FileName.getPath(), Context.MODE_PRIVATE);
            FileOutputStream fos1 = new FileOutputStream(file_);
            fos1.write(output.getBytes());
            fos1.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                // Vocabulary not found
                // ToDo: put popup here



                return ;
            }
           vocabArray_.put(myImportedVoc);


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
            FileOutputStream fos = context_.openFileOutput(file_.getAbsolutePath(), Context.MODE_PRIVATE);
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

    public void deleteVocab() {
        File deleteFile = new File(context_.getFilesDir(), filename_);
        deleteFile.delete();
    }

}