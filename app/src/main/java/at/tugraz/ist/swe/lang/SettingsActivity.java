package at.tugraz.ist.swe.lang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private Button btnStore;
    private Button btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        btnStore = findViewById(R.id.btnCategory);
        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNameDialog();
            }
        });

        btnLoad = findViewById(R.id.btnCategory);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStoreDialog();
            }
        });
    }


    public void openNameDialog() {
        //Intent intent = new Intent(this, XActivity.class);
        //startActivity(intent);
    }

    public void openStoreDialog() {
        //Intent intent = new Intent(this, XActivity.class);
        //startActivity(intent);
    }
}
