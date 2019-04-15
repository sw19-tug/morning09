package at.tugraz.ist.swe.lang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Random;

public class SimpleTestActivity extends AppCompatActivity {

    TextView score;
    TextView question;
    ListView multiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_test);

        score.findViewById(R.id.score);
        question.findViewById(R.id.question);
        multiple.findViewById(R.id.multiple);
    }

}

