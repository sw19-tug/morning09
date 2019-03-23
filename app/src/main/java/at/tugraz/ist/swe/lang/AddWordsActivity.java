package at.tugraz.ist.swe.lang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddWordsActivity extends AppCompatActivity {

    Button saveWord;

    ArrayList<String> wordArray = new ArrayList<String>();

    EditText word;
    ListView show;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);

        word = (EditText)findViewById(R.id.ptAddWord);
        show = (ListView)findViewById(R.id.lvWordList);
        saveWord = (Button)findViewById(R.id.btAdd);
        saveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = word.getText().toString();

                if(wordArray.contains(input)){
                    Toast.makeText(getBaseContext(), "Word already added!", Toast.LENGTH_LONG).show();

                }
                else if(input == null){
                    Toast.makeText(getBaseContext(), "Empty input, try again!", Toast.LENGTH_LONG).show();
                }
                else{
                    wordArray.add(input);
                    ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(AddWordsActivity.this, android.R.layout.simple_list_item_1, wordArray);
                    show.setAdapter(adapterArray);
                    ((EditText)findViewById(R.id.ptAddWord)).setText(" ");
                }
            }
        });
    }
}
