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

                try {
                    JSONArray jsonArray = test.getVocabArray();
                    ArrayList<String> wordArray = new ArrayList<String>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String title = jsonArray.getJSONObject(i).getString("german");
                        if (title.equals("TITLE:")) {
                            String entry = jsonArray.getJSONObject(i).getString("english");
                            if (entry.equals(title_ot_test)) {
                                String rrr;
                                i++;
                                while (jsonArray.getJSONObject(i).getString("german").equals("TITLE:")) {

                                    rrr = jsonArray.getJSONObject(i).getString("english") + " : " + jsonArray.getJSONObject(i).getString("german");
                                    wordArray.add(rrr);
                                    i++;
                                    }
                                break;
                            }


                            }

                        }
                    Toast.makeText(TakeTestActivity.this, "Start test " + title_ot_test + " with " + wordArray.size() + " questions.", Toast.LENGTH_LONG).show();
                    performTest(wordArray);
                    }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void performTest(ArrayList<String> wordArray) {

        setContentView(R.layout.activity_take_test);
        wordToTest = findViewById(R.id.word_to_test);
        testedWord = (EditText)findViewById((R.id.tested_word));
        tryTest = findViewById(R.id.try_test);

        while (wordArray.size() > counter) {
            final String[] words = wordArray.get(counter).split(" : ");
            wordToTest.setText(words[0]);
            tryTest.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (testedWord.getText().toString().equals(words[1])) {
                        counter++;
                        myScore++;
                    }
                    else
                    {
                        Toast.makeText(TakeTestActivity.this, "Error!", Toast.LENGTH_LONG).show();
                    }
                    myAttemps++;
                }
            });
        }
        youWin();
    }

    private void youWin() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TakeTestActivity.this);
        alertDialogBuilder
                .setMessage("Well done!!! You passed your exam with: " +myScore + " Correct Answers in " +myAttemps + " Attemps")
                .setCancelable(false)
                .setPositiveButton("Retry?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                startActivity(new Intent(getApplicationContext(),TakeTestActivity.class));
                                finish();

                            }
                        })
                .setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                finish();

                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}