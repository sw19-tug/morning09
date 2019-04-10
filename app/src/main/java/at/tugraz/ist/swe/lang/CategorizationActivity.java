package at.tugraz.ist.swe.lang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategorizationActivity extends AppCompatActivity {

    Button btnAlphabeticSort;
    Spinner spLanguageSort, spTagSort;
    Vocabulary vocabulary;
    ListView lvVocabulary;

    // Temp
    ArrayList<String> languageList = new ArrayList<String>();
    ArrayList<String> tagList = new ArrayList<String>();
    ArrayList<String> vocabList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorization);

        btnAlphabeticSort = (Button)findViewById(R.id.btnAlphabeticSort);
        spLanguageSort = (Spinner)findViewById(R.id.spLanguageSort);
        spTagSort = (Spinner)findViewById(R.id.spTagSort);
        lvVocabulary = (ListView)findViewById(R.id.lvVocabulary);

        vocabulary = new Vocabulary(getApplicationContext());
        vocabulary.init();

        updateLanguages();
        updateTags();
        updateVocabulary();
        btnAlphabeticSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void updateLanguages() {

        languageList.add("Select language");
        languageList.add("German");
        languageList.add("English");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategorizationActivity.this, android.R.layout.simple_dropdown_item_1line, languageList);
        spLanguageSort.setAdapter(adapter);

    }

    public void updateTags() {

        tagList.add("Select Tag");
        tagList.add("Fruit");
        tagList.add("Hardcore");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategorizationActivity.this, android.R.layout.simple_dropdown_item_1line, tagList);
        spTagSort.setAdapter(adapter);

    }

    public void updateVocabulary() {
        try{
            JSONArray jsonArray = vocabulary.getVocabArray();
            ArrayList<String> wordArray = new ArrayList<String>();

            for(int i=0; i < jsonArray.length();i++){
                String entry = jsonArray.getJSONObject(i).getString("english") + " : " + jsonArray.getJSONObject(i).getString("german");
                wordArray.add(entry);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategorizationActivity.this, android.R.layout.simple_list_item_1, wordArray);
            lvVocabulary.setAdapter(adapter);

        } catch(JSONException e){
            e.printStackTrace();
        }
    }
}
