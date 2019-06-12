package at.tugraz.ist.swe.lang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class TakeTestActivity extends AppCompatActivity {

    private TextView wordToTest;
    private ArrayList<String> wordArray = new ArrayList<String>();
    private TextView title_of_page;
    private EditText testedWord;
    private Button tryTest;
    private Button takeTest;
    private int myScore = 0;
    private int myAttemps = 0;
    ListView tests_list;
    Test test;
    int counter = 0;

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
            ArrayList<String> testArray = new ArrayList<String>();

            for (int i = 0; i < jsonArray.length(); i++) {
                String title = jsonArray.getJSONObject(i).getString("german");
                if (title.equals("TITLE:")) {
                    String entry = jsonArray.getJSONObject(i).getString("english");
                    testArray.add(entry);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TakeTestActivity.this, android.R.layout.simple_list_item_single_choice, testArray);
            tests_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            tests_list.setAdapter(adapter);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        takeTest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_ot_test = (String) tests_list.getAdapter().getItem(tests_list.getCheckedItemPosition());
                int i = 0;
                try {
                    JSONArray jsonArray = test.getVocabArray();
                    //ArrayList<String> wordArray = new ArrayList<String>();

                    for (; i < jsonArray.length(); i++) {
                        String title = jsonArray.getJSONObject(i).getString("german");
                        if (title.equals("TITLE:")) {
                            String entry = jsonArray.getJSONObject(i).getString("english");
                            if (entry.equals(title_ot_test)) {
                                break;
                            }
                        }
                    }
                    i++;
                    for (; i < jsonArray.length(); i++) {
                        String rrr = jsonArray.getJSONObject(i).getString("english") + " : " + jsonArray.getJSONObject(i).getString("german");
                        if (rrr.contains(("TITLE:")))
                            break;
                        else
                            wordArray.add(rrr);
                    }
                    //performTest();

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(TakeTestActivity.this, "Start test " + title_ot_test + " with " + wordArray.size() + " questions.", Toast.LENGTH_LONG).show();
                openPerformTest();
                //performTest(wordArray);
            }
        });
    }

    public void openPerformTest() {
        Intent intent_add = new Intent(this, PerformTest.class);
        Bundle b = new Bundle();
        b.putStringArrayList("wordArray", wordArray);
        intent_add.putExtras(b);
        startActivity(intent_add);

    }

    public ArrayList<String> getArray() {
        return wordArray;
    }

    //private void performTest() {
    //    final View vv = getLayoutInflater().inflate(R.layout.activity_take_test, null );



}