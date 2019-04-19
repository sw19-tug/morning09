package at.tugraz.ist.swe.lang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.Random;

public class SimpleTestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayAdapter adapter;
    private String myAnswer;
    private int myScore = 0;
    private ArrayAdapter answers;
    private int myQuestionLength;
    Random r;
    String[] random_question;
    //protected Questions myQuestions;
    TextView score, question;
    ListView multiple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_test);

        score = findViewById(R.id.score);
        question = findViewById(R.id.question);
        multiple = findViewById(R.id.multiple);
        Questions myQuestions = new Questions();
        String[] a = myQuestions.getItems(myScore);
        score.setText("Score: " + myScore);
        answers = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,a
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
        myScore++;
        Questions myQuestions = new Questions();
        String[] a = myQuestions.getItems(myScore);
        //score.setText("Score: " + myScore);
        updateArray(a);
        updateQuestion(myScore);
    }

    private void updateArray(String[] a) {
        answers = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,a
        );
        multiple.setAdapter(answers);
        score.setText("Score: " + myScore);
    }
}

