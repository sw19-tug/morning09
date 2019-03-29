package at.tugraz.ist.swe.lang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import at.tugraz.ist.swe.lang.R;

public class TranslationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[]  dewords = getResources().getStringArray(R.array.dewords);
        String[]  enwords = getResources().getStringArray(R.array.enwords);

       // String[] chosenLang

        ListView listView = (ListView)findViewById(R.id.word_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dewords
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView <?> parent, View view, int arg2, long arg3) {
        TextView tv = (TextView) view;
        Toast.makeText(this, tv.getText(), Toast.LENGTH_LONG).show();
    }
}
