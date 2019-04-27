package com.example.scrapofood;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scrapofood.Entities.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GroupContent extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_content);

        createLayout();

        Bundle intent = getIntent().getExtras();

        //TODO: fix it - not null -> still not working
        if(intent.getString("GroupId",null) != null ){
            int id =  Integer.parseInt(intent.getString("GroupId","0"));
            ArrayList<User> userList = MainActivity.myGroups.get(id).getUsers();

            ArrayList<String> userNames = new ArrayList<>();
            userList.forEach((g) -> userNames.add(g.getName()));

            //TODO: why is it null? I don't get it
            ListView lv = findViewById(R.id.lvGroupUser);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, userNames);
            lv.setAdapter(adapter);
        }


        //TODO: find alternative filter to work with
        //floating filter
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void createLayout() {
        tabLayout = findViewById(R.id.tabGroupContent);
        viewPager = findViewById(R.id.viewPageContent);
        adapter = new ViewPageAdapter(getSupportFragmentManager());

        //add tabs
        adapter.AddFragment(new FragmentUser(),"Mitglieder");
        adapter.AddFragment(new FragmentFood(),"Essen");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
