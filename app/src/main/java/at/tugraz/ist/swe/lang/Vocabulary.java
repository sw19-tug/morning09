package at.tugraz.ist.swe.lang;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Vocabulary {


    private Map<String,String> vocabularyMap;
    private Context context_;
    public File file_;
    private JSONArray vocabArray_;
    JSONObject vocabulary_;
    private String filename = "vocabulary.json";


    public Vocabulary(Context context) {
        context_ = context;

        File deleteFile = new File(context.getFilesDir(),filename);
        deleteFile.delete();

        //check if file exists
        file_ = new File(context.getFilesDir(), filename);
        if (!file_.exists()) {
            try {
                file_.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //convert to json
        try {
            //for unit tests, we probably need to restructure this
            String data = "";//readFromFile(filename);

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

    public int add(String german, String english)
    {

        try{
            JSONObject newJsonObj = new JSONObject();

            newJsonObj.put("english", english);
            newJsonObj.put("german", german);

            vocabArray_.put(newJsonObj);

            vocabulary_.put("vocabulary",vocabArray_);
        } catch(JSONException e){
            e.printStackTrace();
        }

        storeFile();

        return 0;
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

    public void storeFile() {

        String output = vocabulary_.toString();
        System.out.println(output);
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

    public String readFromFile(String filename) {

        String readString = null;
        try {
            FileInputStream is = context_.openFileInput(filename);
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
}