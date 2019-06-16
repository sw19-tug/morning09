package at.tugraz.ist.swe.lang;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.File;

import static android.icu.lang.UCharacter.toLowerCase;

public class CategorizationActivity extends AppCompatActivity {

    Button btnAlphabeticSort, btnReset;
    Spinner spLanguageSort, spTagSort;
    Vocabulary vocabulary;
    ListView lvVocabulary;
    private JSONArray vocabArray = new JSONArray();
    ArrayList<String> languageList = new ArrayList<String>();
    ArrayList<String> tagList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorization);

        btnAlphabeticSort = (Button)findViewById(R.id.btnAlphabeticSort);
        btnReset = (Button)findViewById(R.id.btnReset);
        spLanguageSort = (Spinner)findViewById(R.id.spLanguageSort);
        spTagSort = (Spinner)findViewById(R.id.spTagSort);
        lvVocabulary = (ListView)findViewById(R.id.lvVocabulary);

        vocabulary = new Vocabulary(getApplicationContext());
        vocabulary.init();
        vocabArray = vocabulary.getVocabArray();


        updateLanguages();
        updateTags();
        updateVocabulary();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spLanguageSort.setSelection(0);
                spTagSort.setSelection(0);
                updateVocabulary();
            }
        });

        btnAlphabeticSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (("Select language".compareTo(spLanguageSort.getSelectedItem().toString()) !=0 ) &&
                        ("Select Tag".compareTo(spTagSort.getSelectedItem().toString()) == 0)){
                    sortAlphabet(spLanguageSort.getSelectedItem().toString());
                }
                else if(("Select language".compareTo(spLanguageSort.getSelectedItem().toString()) != 0 ) &&
                        ("Select Tag".compareTo(spTagSort.getSelectedItem().toString()) != 0)) {
                    filterTags(spTagSort.getSelectedItem().toString());
                    sortAlphabet(spLanguageSort.getSelectedItem().toString());
                }
                else if(("Select language".compareTo(spLanguageSort.getSelectedItem().toString()) == 0 ) &&
                        ("Select Tag".compareTo(spTagSort.getSelectedItem().toString()) != 0)) {
                    filterTags(spTagSort.getSelectedItem().toString());

                } else {
                Toast.makeText(getBaseContext(), "No language or tag selected!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    public void sortAlphabet(String choice) {
        try {
            JSONArray jsonArray = vocabulary.getVocabArray();
            JSONArray sortedJsonArray = new JSONArray();

            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonValues.add(jsonArray.getJSONObject(i));
            }
            final String temp_choice = choice;
            Collections.sort(jsonValues, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String KEY_NAME = temp_choice;
                    String valA = "";
                    String valB = "";

                    try {
                        valA = (String) a.get(KEY_NAME);
                        valB = (String) b.get(KEY_NAME);
                    } catch (JSONException e) {
                    }
                    String lowerA = valA.toLowerCase();
                    String lowerB = valB.toLowerCase();
                    return lowerA.compareTo(lowerB);
                }
            });

            for (int i = 0; i < jsonArray.length(); i++) {
                sortedJsonArray.put(jsonValues.get(i));
            }

            vocabArray = sortedJsonArray;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        updateVocabulary();

    }

    public void filterTags(String choice) {
        // needs tag implementation first, shifting to new issue
        JSONArray jsonArray = vocabulary.getVocabArray();
        ArrayList<String> filteredWordArray = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject currWord = jsonArray.getJSONObject(i);

                if (!currWord.has("tags")) {
                    JSONArray tmpArray = new JSONArray();
                    currWord.put("tags", tmpArray);
                } else {
                    System.out.println("iterate tags for this word.");
                    JSONArray tagsFromFile = currWord.getJSONArray("tags");

                    // Iterate the tags in current word.
                    for(int j=0; j < tagsFromFile.length();j++){
                        System.out.println(tagsFromFile.getString(j));

                        if (choice.equals(tagsFromFile.getString(j))) {
                            System.out.println("currWord contains this Tag");
                            System.out.println("currWord " + currWord.get("german") + " gets added to new list.");

                            String entry = currWord.getString("english") + " : " + currWord.getString("german");
                            // Add word to new filtered List.
                            filteredWordArray.add(entry);
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(CategorizationActivity.this, android.R.layout.simple_list_item_1, filteredWordArray);
        lvVocabulary.setAdapter(adapter1);
    }

    public void updateLanguages() {

        languageList.add("Select language");
        languageList.add("german");
        languageList.add("english");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategorizationActivity.this, android.R.layout.simple_dropdown_item_1line, languageList);
        spLanguageSort.setAdapter(adapter);

    }

    public void updateTags() {


        JSONArray jsonArray = vocabulary.getVocabArray();

        // Iterate the words in vocabulary.
        ArrayList<String> tagsArray = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject currWord = jsonArray.getJSONObject(i);

                if (!currWord.has("tags")) {
                    JSONArray tmpArray = new JSONArray();
                    currWord.put("tags", tmpArray);
                } else {
                    System.out.println("iterate tags for this word.");
                    JSONArray tagsFromFile = currWord.getJSONArray("tags");

                    // Iterate the tags in current word.
                    for(int j=0; j < tagsFromFile.length();j++){
                        System.out.println(tagsFromFile.getString(j));
                        if (!tagsArray.contains(tagsFromFile.getString(j))) {
                            tagsArray.add(tagsFromFile.getString(j));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Iterte over tagsArray and fill the dropdwon.
        if (tagsArray.isEmpty()) {
            tagList.add("Select Tag");
        } else {
            tagList.add("Select Tag");
            for (int i=0; i < tagsArray.size(); i++) {
                System.out.println("Add " + tagsArray.get(i) + " tag to dropdown list.");
                tagList.add(tagsArray.get(i));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategorizationActivity.this, android.R.layout.simple_dropdown_item_1line, tagList);
        spTagSort.setAdapter(adapter);
    }

    public void updateVocabulary() {
        try {
            final JSONArray jsonArray = vocabArray;//
            ArrayList<String> wordArray = new ArrayList<String>();

            for (int i = 0; i < jsonArray.length(); i++) {
                String entry = jsonArray.getJSONObject(i).getString("english") + " : " + jsonArray.getJSONObject(i).getString("german");
                wordArray.add(entry);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(CategorizationActivity.this, android.R.layout.simple_list_item_1, wordArray);
            lvVocabulary.setAdapter(adapter);
            lvVocabulary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //setContentView(R.layout.activity_rating);
                    //openRatingActivity(position);

                    System.out.println("Position Value is:");
                    String Value = (String) lvVocabulary.getItemAtPosition(position);

                    System.out.println(position);
                    System.out.println(Value);

                    try {

                        final JSONArray jsonArray1 = jsonArray;
                        JSONObject export = jsonArray1.getJSONObject(position);
                        String exportString = export.toString();


                        if(!checkPermission()){
                            requestPermission();
                        }else {
                            writeToFile(exportString);
                        }
                        File exportFile = new File("/storage/emulated/0/Download/export_vocabulary.json");
                        vocabulary.exportVocabularyToFile(export, exportFile);
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/*");

                        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + exportFile.getAbsolutePath()));
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "You've got a Word and it is "+ Value);
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, "A friend would like you too, to be able to intoxicate society with the exuberance of your verbosity. \n This tremendous ne Word is \n \n: "+Value + "\n \n");
                       sharingIntent.putExtra(Intent.EXTRA_TEXT, "To import this word, save the attached file on your Device\n" +
                               "Go to \"Add a Word\" \n Then \"Import a Word from File\"\n" +
                               "Lastly tap on the downloaded file and it is imported, enjoy ");
                        startActivity(Intent.createChooser(sharingIntent, "SHARE THE WORD"));
                        //Toast.makeText(getBaseContext(), exportFile.getAbsolutePath(), Toast.LENGTH_LONG).show();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                    e.printStackTrace();
                }


                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void writeToFile(String content) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/export.txt");


            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    protected boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result1 == PackageManager.PERMISSION_GRANTED  ) {
            return true;
        } else {
            return false;
        }
    }

    protected void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                }
                break;
        }
    }

}
