package at.tugraz.ist.swe.lang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button addWords;
    private Button translation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addWords = findViewById(R.id.btMainAddWords);
        addWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddWordsActivity();
            }
        });

        translation = findViewById(R.id.main_btn_translate);
        translation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTranslationActivity();
            }
        });
    }

    public void openAddWordsActivity() {
        Intent intent = new Intent(this, AddWordsActivity.class);
        startActivity(intent);

    }

    public void openTranslationActivity() {
        Intent intent = new Intent(this, TranslationActivity.class);
        startActivity(intent);

    }
}
