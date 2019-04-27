package com.example.scrapofood;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GroupContent extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_content);

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
