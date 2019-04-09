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


    private Context context_;
    public File file_;
    private JSONArray vocabArray_;
    JSONObject vocabulary_;
    private String filename = "vocabulary.json";


    public Vocabulary(Context context) {
        context_ = context;

        //uncomment if you are testing and want a new vocab
        //File deleteFile = new File(context.getFilesDir(),filename);
        //deleteFile.delete();

        //check if file exists
        file_ = new File(context.getFilesDir(), filename);
        if (!file_.exists()) {
            try {
                file_.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

                if(entry.getString("german") == vocabularyName)
                {
                    return i;
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

        return -1;
    }

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

    public String readFromFile() {

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

    public JSONArray getVocabArray() {
        return vocabArray_;
    }
}