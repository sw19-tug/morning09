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

public class Test {

    private Context context_;
    public File file_;
    private JSONArray testArray_;
    JSONObject test_;
    static String filename_ = "test.json";


    public Test(Context context) {
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
                test_ = new JSONObject(data);
                testArray_ = test_.getJSONArray("test");
            } else {
                test_ = new JSONObject();
                testArray_ = new JSONArray();
                test_.put("test",testArray_);
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

            testArray_.put(newJsonObj);

            test_.put("test",testArray_);
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
    /*public int findByName(String vocabularyName)
    {
        try {
            for(int i=0; i < testArray_.length();i++){
                JSONObject entry = testArray_.getJSONObject(i);

                if(entry.getString("german").equals(vocabularyName))
                {
                    return i;
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return -1;
    } */

    /*public String[] findTitles() //TODO title
    {
        String[] names = null;
        int name_counter = 0;
        try {
            for(int i=0; i < testArray_.length();i++){
                JSONObject entry = testArray_.getJSONObject(i);

                if(entry.getString("german").equals("TITLE:"))
                {
                    names[name_counter] = entry.getString(("english"));
                    name_counter++;
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return names;
    } */


    /**
     * Stores Json into File
     */
    public void storeFile() {

        String output = test_.toString();
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
        return testArray_;
    }

    /**
     * Store to file by filename
     * @param filename
     */
    public void storeFileByName(String filename) {

        String output = test_.toString();
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
            FileOutputStream fos = context_.openFileOutput(filename, Context.MODE_PRIVATE);
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
