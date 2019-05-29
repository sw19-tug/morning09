package at.tugraz.ist.swe.lang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;


public class CreateTestActivity extends AppCompatActivity {

    private Button saveTest;
    private ListView vocabList;
    Vocabulary vocabulary;
    JSONArray vocabArray = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_create);

        saveTest = findViewById(R.id.saveTest_btn);
        vocabList = (ListView)findViewById(R.id.vocabList);
        vocabulary = new Vocabulary(getApplicationContext());
        vocabulary.init();
        vocabArray = vocabulary.getVocabArray();

        try {
            JSONArray jsonArray = vocabulary.getVocabArray();
            ArrayList<String> wordArray = new ArrayList<String>();

            for (int i = 0; i < jsonArray.length(); i++) {
                String entry = jsonArray.getJSONObject(i).getString("english") + " : " + jsonArray.getJSONObject(i).getString("german");
                wordArray.add(entry);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateTestActivity.this, android.R.layout.simple_list_item_1, wordArray);
            vocabList.setAdapter(adapter);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
