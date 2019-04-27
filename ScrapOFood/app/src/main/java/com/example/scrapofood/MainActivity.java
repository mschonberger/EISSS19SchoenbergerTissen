package com.example.scrapofood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scrapofood.Entities.Group;
import com.example.scrapofood.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String SAVED_NAME = "text";
    public static final String SHARED_PREFS = "sharedPrefs";

    public static List<Group> myGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dummy data for testing
        addDummyDataForTesting();

        loadName();

        next();
    }

    private void addDummyDataForTesting() {
        //init groups
        Group myHouse = new Group("MyHouse");
        Group myStreet = new Group("MyStreet");
        Group myFriends = new Group("MyFriends");

        //add groups
        myGroups = new ArrayList<>();
        myGroups.add(myHouse);//0
        myGroups.add(myStreet);//1
        myGroups.add(myFriends);//2

        //add user to "myHouse" group
        myGroups.get(0).addUser(new User("Gabi"));
        myGroups.get(0).addUser(new User("Kora"));
        myGroups.get(0).addUser(new User("Andre"));

        //add user to "myStreet" group
        myGroups.get(1).addUser(new User("Arnold"));

        //add user to "myFriends" group
        myGroups.get(2).addUser(new User("Malte"));
        myGroups.get(2).addUser(new User("Ingrid"));
        myGroups.get(2).addUser(new User("Peter"));
        myGroups.get(2).addUser(new User("Gustav"));
        myGroups.get(2).addUser(new User("Dieter"));
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
