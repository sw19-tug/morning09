package at.tugraz.ist.swe.lang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;

public class TestsActivity extends AppCompatActivity {

    Button btnCreateTest;
    Button btnTakeTest;
    ListView vocabList;
    Vocabulary vocabulary;
    JSONArray vocabArray = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        btnCreateTest = findViewById(R.id.btnCreateTest);
        btnCreateTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { setContentView(R.layout.activity_test_create);
            }
        });
        vocabList = (ListView)findViewById(R.id.vocabList);
        vocabulary = new Vocabulary(getApplicationContext());
        vocabulary.init();
        vocabArray = vocabulary.getVocabArray();

    }
}

