package at.tugraz.ist.swe.lang;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddWordsActivity extends AppCompatActivity {

    Button btnAdd;
    EditText ptAddEnglish, ptAddGerman;
    ListView lvWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);

        ptAddEnglish = (EditText)findViewById(R.id.ptAddEnglish);
        ptAddGerman = (EditText)findViewById(R.id.ptAddGerman);
        lvWordList = (ListView)findViewById(R.id.lvWordList);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        // uncomment to delete vocab file everytime

        /*File deleteFile = new File(getApplicationContext().getFilesDir(),"vocabulary.json");
        deleteFile.delete();*/


        File file = new File(getApplicationContext().getFilesDir(), "vocabulary.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        updateListView();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String englishWord = ptAddEnglish.getText().toString();
                String germanWord = ptAddGerman.getText().toString();

                if(englishWord.length() == 0 || germanWord.length() == 0){
                    Toast.makeText(getBaseContext(), "Empty input!", Toast.LENGTH_LONG).show();
                }
                else{
                    insertToJson(getApplicationContext(), englishWord, germanWord);
                }

                updateListView();

                ptAddEnglish.getText().clear();
                ptAddGerman.getText().clear();
            }
        });
    }

    public void updateListView() {
        try{
            String data = readFromFile(getApplicationContext(), "vocabulary.json");
            JSONObject newJsonData = new JSONObject(data);
            JSONArray jsonArray = newJsonData.getJSONArray("vocabulary");

            ArrayList<String> wordArray = new ArrayList<String>();

            for(int i=0; i < jsonArray.length();i++){
                String entry = jsonArray.getJSONObject(i).getString("english") + " : " + jsonArray.getJSONObject(i).getString("german");
                wordArray.add(entry);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddWordsActivity.this, android.R.layout.simple_list_item_1, wordArray);
            lvWordList.setAdapter(adapter);

        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void insertToJson(Context context, String englishWord, String germanWord) {
        try{
            String json = readFromFile(context, "vocabulary.json");

            JSONObject existingJsonData;
            JSONObject newJsonData = new JSONObject();
            JSONObject newJsonObj = new JSONObject();

            newJsonObj.put("english", englishWord);
            newJsonObj.put("german", germanWord);

            JSONArray jsonArray;

            if(json.length() > 0){
                existingJsonData = new JSONObject(json);
                jsonArray = existingJsonData.getJSONArray("vocabulary");
            } else {
                jsonArray = new JSONArray();
            }

            jsonArray.put(newJsonObj);
            newJsonData.put("vocabulary",jsonArray);
            insertToFile(context, "vocabulary.json",  newJsonData.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertToFile(Context context, String filename, String saveString) {

        try{
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(saveString.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromFile(Context context, String filename) {

        String readString = null;
        try {
            InputStream is = context.openFileInput(filename);
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