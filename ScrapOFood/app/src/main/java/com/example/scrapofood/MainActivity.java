package com.example.scrapofood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String SAVED_NAME = "text";
    public static final String SHARED_PREFS = "sharedPrefs";

    //private EditText textViewName = findViewById(R.id.etName);
    //private Button buttonContinue = findViewById(R.id.btnContinue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: add dummy user for testing
        //Group g = new Group();
        //g.Users.add(new User("Arthur"));
        //g.Users.add(new User("Malte"));
        //g.Users.add(new User("Ingrid"));
        //g.Users.add(new User("Peter"));
        //g.Users.add(new User("Gustav"));
        //g.Users.add(new User("Hans"));

        loadName();

        next();
    }

    /* sends user to the next activity (currently group panel) */
    private void next() {
        Button buttonContinue = findViewById(R.id.btnContinue);
        buttonContinue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveName();
                startActivity(new Intent(MainActivity.this, MainGroups.class));
            }
        });

    }

    /* Saving method for current name/user */
    private void saveName() {
        EditText editTextName = findViewById(R.id.etName);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SAVED_NAME, editTextName.getText().toString());

        editor.apply(); //finally saves the set data
        Toast.makeText(this, "Name was set", Toast.LENGTH_SHORT).show(); //feedback for user that the name wa sset
    }

    /* Loading method for current name/user */
    private void loadName(){
        EditText et = findViewById(R.id.etName);
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        et.setText(sp.getString(SAVED_NAME, ""));
    }
}
