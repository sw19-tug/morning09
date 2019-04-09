package at.tugraz.ist.swe.lang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class AddWordsActivity extends AppCompatActivity {

    Button btnAdd;
    EditText ptAddEnglish, ptAddGerman;
    ListView lvWordList;
    Vocabulary vocabulary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);

        ptAddEnglish = (EditText)findViewById(R.id.ptAddEnglish);
        ptAddGerman = (EditText)findViewById(R.id.ptAddGerman);
        lvWordList = (ListView)findViewById(R.id.lvWordList);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        vocabulary = new Vocabulary(getApplicationContext());
        vocabulary.init();

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
                    vocabulary.add(germanWord,englishWord);
                    vocabulary.storeFile();
                    updateListView();
                }
                ptAddEnglish.getText().clear();
                ptAddGerman.getText().clear();
            }
        });
    }

    public void updateListView() {
        try{
            JSONArray jsonArray = vocabulary.getVocabArray();
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
}