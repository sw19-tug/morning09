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
        final ArrayList<String> wordArray = new ArrayList<String>();


        //FOR TESTING ALWAYS DELETES VOCAB FIRST
        File deleteFile = new File(getApplicationContext().getFilesDir(),"vocabulary.json");
        deleteFile.delete();

        File file = new File(getApplicationContext().getFilesDir(), "vocabulary.json");
        try {
            file.createNewFile();
        } catch (Exception e) {
            Log.d("FILE_CREATION", "new file wasn't created");
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                String englishWord = ptAddEnglish.getText().toString();
                String germanWord = ptAddGerman.getText().toString();

                if(englishWord.length() == 0 || germanWord.length() == 0){
                    Toast.makeText(getBaseContext(), "Empty input!", Toast.LENGTH_LONG).show();
                }
                else{
                    insertToJson(englishWord, germanWord);
                }


                try{
                    String data = readFromJson();
                    JSONObject newJsonDB = new JSONObject(data);
                    JSONArray jsonArray = newJsonDB.getJSONArray("vocabulary");

                    for(int i=0; i < jsonArray.length();i++){
                        String entry = jsonArray.getJSONObject(i).getString("englishWord") + " : " + jsonArray.getJSONObject(i).getString("germanWord");
                        wordArray.add(entry);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddWordsActivity.this, android.R.layout.simple_list_item_1, wordArray);

                    lvWordList.setAdapter(adapter);
                }
                catch(Exception e){

                }


            }



        });
    }

    public void insertToJson(String englishWord, String germanWord) {

        try
        {
            String json = readFromJson();


            JSONObject existingJsonDB;
            JSONObject newJsonDB = new JSONObject();


            JSONObject newJsonObj = new JSONObject();

            newJsonObj.put("englishWord", englishWord);
            newJsonObj.put("germanWord", germanWord);

            JSONArray jsonArray;



            if(json.length() > 0){
                existingJsonDB = new JSONObject(json);
                jsonArray = existingJsonDB.getJSONArray("vocabulary");
            }
            else
            {
                jsonArray = new JSONArray();
            }

            jsonArray.put(newJsonObj);
            newJsonDB.put("vocabulary",jsonArray);

            String jsonString = newJsonDB.toString();
            Log.d("INSERT", jsonString);
            FileOutputStream fos = getApplicationContext().openFileOutput("vocabulary.json", Context.MODE_PRIVATE);

            fos.write(jsonString.getBytes());
            fos.close();

        } catch (FileNotFoundException fileNotFound) {
            Log.d("INSERT_JASON", "File not found");
        } catch (IOException ioException) {
            Log.d("INSERT_JASON", "I/O Exception");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("INSERT_JASON", "something else");
        }
    }

    public String readFromJson() {
        String json = null;
        try{
            InputStream is = getApplicationContext().openFileInput("vocabulary.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        }
        catch(IOException e){
            Log.d("Read Json", "exception");

            e.printStackTrace();

        }
        if(json == null){
            Log.d("Read Json", "shit");

        }

        return json;

    }
}
