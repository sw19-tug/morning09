package at.tugraz.ist.swe.lang;

import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddWordsActivity extends AppCompatActivity {

    Button btnAdd;
    EditText ptAddEnglish, ptAddGerman;
    ListView lvWordList;
    Vocabulary vocabulary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);

        ptAddEnglish = (EditText)findViewById(R.id.ptAddEnglish);
        ptAddGerman = (EditText)findViewById(R.id.ptAddGerman);
        lvWordList = (ListView)findViewById(R.id.lvWordList);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        vocabulary = new Vocabulary(getApplicationContext());
        vocabulary.init();

        updateListView();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String englishWord = ptAddEnglish.getText().toString();
                String germanWord = ptAddGerman.getText().toString();

                if(englishWord.length() == 0 || germanWord.length() == 0){
                    Toast.makeText(getBaseContext(), "Empty input!", Toast.LENGTH_LONG).show();
                }
                else{
                    vocabulary.add(germanWord,englishWord);
                    vocabulary.storeFile();
                    updateListView();
                }
                ptAddEnglish.getText().clear();
                ptAddGerman.getText().clear();
            }
        });
    }

    public void updateListView() {
        try{
            JSONArray jsonArray = vocabulary.getVocabArray();
            ArrayList<String> wordArray = new ArrayList<String>();

            for(int i=0; i < jsonArray.length();i++){
                String entry = jsonArray.getJSONObject(i).getString("english") + " : " + jsonArray.getJSONObject(i).getString("german");
                wordArray.add(entry);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddWordsActivity.this, android.R.layout.simple_list_item_1, wordArray);
            lvWordList.setAdapter(adapter);
            lvWordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //setContentView(R.layout.activity_rating);
                    //openRatingActivity(position);

                    System.out.println("Position Value is:");
                    String Value = (String) lvWordList.getItemAtPosition(position);

                    System.out.println(Value);
                    openRatingActivity(position);
                    System.out.println(Value);


                }

            });

        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void openRatingActivity(int position) {
        setContentView(R.layout.activity_rating);

        TextView textViewEn = (TextView)findViewById(R.id.textViewEn);
        TextView textViewDe = (TextView)findViewById(R.id.textViewDe);

        try {
            System.out.println("Position Clicked is:");
            System.out.println(position);
            System.out.println("Position Clicked was:");

            String vocItem = (String) lvWordList.getItemAtPosition(position);
            String[] vocArray = vocItem.split(":");
            final String vocToFind = vocArray[1].trim();

            final JSONArray myJson = vocabulary.getVocabArray();
            final int objectId = vocabulary.findByName(vocToFind);
            JSONObject currWord= myJson.getJSONObject(objectId);

            textViewEn.setText(currWord.getString("english"));
            textViewDe.setText(currWord.getString("german"));

            final RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
            if (!currWord.has("rating")) {
                currWord.put("rating", 2);
            } else {
                ratingBar.setRating((float)currWord.getDouble("rating"));
            }

            final ArrayList<String> tagsArray = new ArrayList<String>();
            if (!currWord.has("tags")) {
                JSONArray tmpArray = new JSONArray();
                currWord.put("tags", tmpArray);
            } else {
                System.out.println(currWord.getString("tags"));
                JSONArray tagsFromFile = currWord.getJSONArray("tags");

                for(int i=0; i < tagsFromFile.length();i++){
                    tagsArray.add(tagsFromFile.getString(i));
                }
            }

            final ListView lvTags = (ListView)findViewById(R.id.lvTags);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddWordsActivity.this, android.R.layout.simple_list_item_1, tagsArray);
            lvTags.setAdapter(adapter);

            Button addTagsButton = (Button)findViewById(R.id.btnAddTag);
            addTagsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText tagsText = findViewById(R.id.ptNewTag);
                    String toAddTag = tagsText.getText().toString();
                    tagsArray.add(toAddTag);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddWordsActivity.this, android.R.layout.simple_list_item_1, tagsArray);
                    lvTags.setAdapter(adapter);

                    tagsText.setText("");
                }
            });

            Button submitButton = (Button)findViewById(R.id.btnRatingSubmit);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float rating = ratingBar.getRating();

                    vocabulary.addRating(objectId, rating);
                    vocabulary.addTags(objectId, tagsArray);

                    //TODO go back to overview.
                }
            });

        }
        catch(JSONException e) {
            System.out.println("exceptoin @ openRatingActivity");
            e.printStackTrace();
        }
    }
}
