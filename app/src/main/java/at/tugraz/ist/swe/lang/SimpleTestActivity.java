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
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class SimpleTestActivity extends AppCompatActivity {
    private String correctAnswer;
    public int myScore = 0;
    public int myAttemps = 0;
    private ArrayAdapter answers;
    ArrayList<String> quiz = new ArrayList<String>();
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
        generateQuiz("english", 6);
        updateTest();

        multiple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myAttemps++;
                correctAnswer = vocabulary.getTranslation("german", question.getText().toString());
                String choice =  (String) multiple.getItemAtPosition(position);
                if (correctAnswer.equals(choice)) {
                    myScore++;
                    if (myScore == 6) {
                        score.setText("Score: " + myScore);
                        youWin();
                        myScore = 0;
                    }
                    else {
                        updateTest();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid answer",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void updateTest () {
        question.setText(quiz.get(myScore));
        correctAnswer = vocabulary.getTranslation("german", question.getText().toString());
        ArrayList<String> words = generateWords("german", 5, quiz.get(myScore));

        answers = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);
        multiple.setAdapter(answers);
        score.setText("Score: " + myScore);
    }

    public void youWin() {
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

    public void generateQuiz(String lang, int quantity) {
        JSONArray jsonArray = vocabulary.getVocabArray();
        Random rand = new Random();
        ArrayList<Integer> quiz_words = new ArrayList<Integer>();
        quantity = (quantity >= jsonArray.length()) ? jsonArray.length() : quantity;
        for (int i=0; i<quantity; i++) {
            int random = rand.nextInt(jsonArray.length());

            while (quiz_words.contains(random)) {
                random = rand.nextInt(jsonArray.length());
            }

            quiz_words.add(random);

            try {
                quiz.add(jsonArray.getJSONObject(random).getString(lang));
            }
            catch(JSONException e){
            e.printStackTrace();
            }
        }
    }

    public ArrayList<String> generateWords(String lang, int quantity, String dupe) {
        JSONArray jsonArray = vocabulary.getVocabArray();
        Random rand = new Random();
        ArrayList<Integer> words_index = new ArrayList<Integer>();
        ArrayList<String> words = new ArrayList<String>();
        quantity = (quantity >= jsonArray.length()) ? jsonArray.length() : quantity;
        String con_lang = (lang.equals("english")) ? "german" : "english";
        String correct = vocabulary.getTranslation(con_lang, dupe);
        for (int i=0; i<quantity-1; i++) {
            int random;

            try {
                do {
                    random = rand.nextInt(jsonArray.length());
                } while (words_index.contains(random));
                words_index.add(random);
                if (words.contains(correct)) {
                    i--;
                    words.remove(correct);
                }
                words.add(jsonArray.getJSONObject(random).getString(lang));
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        words.add(correct);
        Collections.shuffle(words);
        return words;
    }
}

