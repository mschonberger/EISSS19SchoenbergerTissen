package com.example.scrapofood;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.scrapofood.MainActivity.SAVED_NAME;
import static com.example.scrapofood.MainActivity.SHARED_PREFS;

public class MainGroups extends AppCompatActivity {
    private String currentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_groups);

        back();

        loadName();
    }

    //TODO: vllt irgendwann mal einen Helper dafür anlegen - hat die ersten Versuche nicht geklappt :(

    /* loading method for current name/user */
    private void loadName() {
        TextView tv = findViewById(R.id.tvWelcomeUser);
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        tv.setText("Willkommen zurück " + sp.getString(SAVED_NAME, ""));
    }

    /* Brings the user back to the 'Main' screen */
    private void back() {
        Button btnBack = findViewById(R.id.btnHome); //find and get the button to use
        btnBack.setOnClickListener(new View.OnClickListener() { //set a click listener for the button
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
