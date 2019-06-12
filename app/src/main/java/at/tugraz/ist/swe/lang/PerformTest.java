package at.tugraz.ist.swe.lang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PerformTest extends AppCompatActivity {

    private TextView wordToTest;
    private ArrayList<String> wordArray = new ArrayList<String>();
    private EditText testedWord;
    private Button tryTest;
    private int myScore = 0;
    private int myAttemps = 0;
    int counter = 0;
    String[] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);
        wordToTest = findViewById(R.id.word_to_test);
        testedWord = (EditText)findViewById((R.id.tested_word));
        tryTest = findViewById(R.id.try_test);

        Bundle b = getIntent().getExtras();
        wordArray = b.getStringArrayList("wordArray");
        words = wordArray.get(counter).split(" : ");
        wordToTest.setText(words[0]);

        tryTest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAttemps++;
                if (testedWord.getText().toString().equals(words[1])) {
                    Toast.makeText(PerformTest.this, "You are a boss!", Toast.LENGTH_LONG).show();
                    counter++;
                    myScore++;
                    words = wordArray.get(counter).split(" : ");
                    wordToTest.setText(words[0]);
                    if (counter == wordArray.size())
                        youWin();

                }
                else {
                    Toast.makeText(PerformTest.this, "Error!", Toast.LENGTH_LONG).show();
                }
            }
        });


        /*while (counter < wordArray.size()) {
            words = wordArray.get(counter).split(" : ");
            System.out.println(words);
            wordToTest.setText(words[0]);


        } */
        //youWin();
    }

    private void youWin() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PerformTest.this);
        alertDialogBuilder
                .setMessage("Well done!!! You passed your exam with: " + myScore + " Correct Answers in " +myAttemps + " Attemps")
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
