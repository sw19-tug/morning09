package at.tugraz.ist.swe.lang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class TestsActivity extends AppCompatActivity {

    Button btnCreateTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        btnCreateTest = findViewById(R.id.btnCreateTest);
        btnCreateTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateTest();
            }
        });


    }

    private void openCreateTest() {
        Intent create_advance_test = new Intent(this, CreateTestActivity.class);
        startActivity(create_advance_test);
    }
}

