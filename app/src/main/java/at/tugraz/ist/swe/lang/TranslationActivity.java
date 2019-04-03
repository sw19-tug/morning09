package at.tugraz.ist.swe.lang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class TranslationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayAdapter adapter_de;
    private ArrayAdapter adapter_en;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        String[] dewords = getResources().getStringArray(R.array.dewords);
        String[] enwords = getResources().getStringArray(R.array.enwords);
        Button btn_de = findViewById(R.id.de_btn);
        Button btn_en = findViewById(R.id.en_btn);

        final ListView listView = (ListView) findViewById(R.id.word_list);
        adapter_de = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dewords
        );
        adapter_en = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, enwords
        );
        listView.setAdapter(adapter_de);
        btn_de.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                listView.setAdapter(adapter_de);
            }
        });
        btn_en.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                listView.setAdapter(adapter_en);
            }
        });
        listView.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView <?> parent, View view, int arg2, long arg3) {
        TextView text = (TextView)findViewById(R.id.translated_word);
        String[] sel_word;
        if (parent.getAdapter() == adapter_de) {
            sel_word = getResources().getStringArray(R.array.enwords);
        }
        else
        {
            sel_word = getResources().getStringArray(R.array.dewords);
        }
        text.setText(sel_word[arg2]);
    }
}
