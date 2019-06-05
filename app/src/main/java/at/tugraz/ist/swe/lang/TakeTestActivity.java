package at.tugraz.ist.swe.lang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TakeTestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView wordToTest;
    private TextView title_of_page;
    private EditText testedWord;
    private Button tryTest;
    private Button takeTest;
    private int myScore = 0;
    private int myAttemps = 0;
    private ArrayAdapter tests;
    private String[] tests_name;
    ListView tests_list;
    Test test;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test);
        takeTest = findViewById(R.id.btnTakeTest);
        tests_list = findViewById(R.id.testList);
        title_of_page = findViewById(R.id.titleView);

        test = new Test(getApplicationContext());
        test.init();

        try {
            JSONArray jsonArray = test.getVocabArray();
            ArrayList<String> wordArray = new ArrayList<String>();

            for (int i = 0; i < jsonArray.length(); i++) {
                String entry = jsonArray.getJSONObject(i).getString("english") + " : " + jsonArray.getJSONObject(i).getString("german");
                wordArray.add(entry);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TakeTestActivity.this, android.R.layout.simple_list_item_multiple_choice, wordArray);
            tests_list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            tests_list.setAdapter(adapter);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //tests_name = test.findTitles();
        //tests = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tests_name
        //);
        //tests_list.setAdapter(tests);
        //tests_list.setOnItemClickListener(this);





        /*wordToTest = findViewById(R.id.word_to_test);
        testedWord = findViewById(R.id.tested_word);
        tryTest = findViewById(R.id.try_test);

        wordToTest.setText("Try");

        tryTest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAttemps++;
                String word1 = wordToTest.getText().toString();
                String word2 = testedWord.getText().toString();
                if (word1.equals(word2))
                {
                    myScore++;
                    Toast.makeText(TakeTestActivity.this,"Well done!!! You passed your " +
                            "exam with: " +myScore + " Correct Answers in " +myAttemps + " Attemps", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(TakeTestActivity.this,"Failed", Toast.LENGTH_LONG).show();
                }
            }
        }); */
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}