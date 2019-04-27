package com.example.scrapofood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scrapofood.Entities.Group;

import java.util.ArrayList;
import java.util.List;

import static com.example.scrapofood.MainActivity.SAVED_NAME;
import static com.example.scrapofood.MainActivity.SHARED_PREFS;
import static com.example.scrapofood.MainActivity.myGroups;

public class MainGroups extends AppCompatActivity {
    private String currentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_groups);

        updateListView();

        back();

        loadName();
    }


    private void updateListView() {
        ListView lvGroups = findViewById(R.id.lvGroups);

        //Beispiel
        //MainActivity.myGroups.forEach((k)->System.out.println("bab"+ k + "test"));

        //MainActivity.myGroups.forEach((k)->lvGroups.add

        lvGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //selected item
                String selectedItem = (String)parent.getItemAtPosition(position);

                Intent intent = new Intent(MainGroups.this, GroupContent.class);
                intent.putExtra("GroupName", selectedItem);
                intent.putExtra("GroupId", position);

                //Testing
                Toast.makeText(MainGroups.this, selectedItem, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });

        //add names to new ArrayList for usage
        ArrayList<String> groupNames = new ArrayList<>();
        myGroups.forEach((g) -> groupNames.add(g.getName()));

        //create adapter to list group names in listview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groupNames);
        lvGroups.setAdapter(adapter);
    }

    //TODO: vllt irgendwann mal einen Helper dafür anlegen - hat die ersten Versuche nicht geklappt :(

    /* loading method for current name/user */
    private void loadName() {
        TextView tv = findViewById(R.id.tvWelcomeUser);
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        tv.setText("Willkommen zurück " + sp.getString(SAVED_NAME, "NoNameSet"));
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
