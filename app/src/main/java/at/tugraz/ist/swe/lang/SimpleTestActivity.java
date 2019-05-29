package at.tugraz.ist.swe.lang;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Random;

public class SimpleTestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String myAnswer;
    public int myScore = 0;
    private int myAttemps = 0;
    private ArrayAdapter answers;
    String[] random_question;
    ArrayList<String> quiz;
    TextView score, question;
    ListView multiple;
    Vocabulary vocabulary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_test);

        vocabulary = new Vocabulary(getApplicationContext());
        vocabulary.init();

        score = findViewById(R.id.score);
        question = findViewById(R.id.tvQuestion);
        multiple = findViewById(R.id.multiple);
        updateQuestion(myScore);
        score.setText("Score: " + myScore);
        answers = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,random_question
        );
        multiple.setAdapter(answers);
        multiple.setOnItemClickListener(this);
    }

    private void updateQuestion(int num) {
        Questions myQuestions = new Questions();
        question.setText(myQuestions.getQuestion(num));
        random_question = myQuestions.getItems(num);
        myAnswer = myQuestions.getCorrectAnswer(num);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        myAttemps++;
        if (random_question[position] == myAnswer) {
            myScore++;
            if (myScore == 6) {
                youWin();
            }
            else {
                Questions myQuestions = new Questions();
                String[] newitems = myQuestions.getItems(myScore);
                updateArray(newitems);
                updateQuestion(myScore);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Invalid answer",Toast.LENGTH_SHORT).show();
        }
    }

    private void youWin() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SimpleTestActivity.this);
        alertDialogBuilder
                .setMessage("Well done!!! You passed your exam with: " +myScore + " Correct Answers in " +myAttemps + " Attemps")
                .setCancelable(false)
                .setPositiveButton("Retry?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                startActivity(new Intent(getApplicationContext(),SimpleTestActivity.class));
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

    private void updateArray(String[] newview) {
        answers = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,newview
        );
        multiple.setAdapter(answers);
        score.setText("Score: " + myScore);
    }

    public void generateQuestions(int quantity) {
        JSONArray jsonArray = vocabulary.getVocabArray();

        for (int i=0; i<quantity; i++) {
            int random = 1;
            try {
                jsonArray.getJSONObject(random).getString("german");
            }
            catch(JSONException e){
            e.printStackTrace();
        }
        }
    }
}

