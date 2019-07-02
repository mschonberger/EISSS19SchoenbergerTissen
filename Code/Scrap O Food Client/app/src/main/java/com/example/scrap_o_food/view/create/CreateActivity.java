package com.example.scrap_o_food.view.create;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scrap_o_food.R;
import com.example.scrap_o_food.view.home.MainActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDrawer();

    }

    public void createAd() {

        /*private static final String TAG = "CreateActivity";

        private static final String KEY_TITLE = "title";
        private static final String KEY_DESCRIPTION = "description";

        private EditText editTextTitle;
        private EditText editTextDescription;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create);

            createDrawer();

            editTextTitle = findViewById(R.id.edit_text_title);
            editTextDescription = findViewById(R.id.edit_text_description);

        }

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        Map<String, Object> ad = new HashMap<>();
        ad.put(KEY_TITLE, title);
        ad.put(KEY_DESCRIPTION, description); */

    }

    public void createDrawer() {

        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withName("Home")
                .withIcon(R.drawable.ic_home)
                .withIdentifier(1);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem()
                .withName("Inserat erstellen")
                .withIcon(R.drawable.ic_addfood)
                .withIdentifier(2);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem()
                .withName("Mein Profil")
                .withIcon(R.drawable.ic_person)
                .withIdentifier(3);
        PrimaryDrawerItem item4= new PrimaryDrawerItem()
                .withName("Meine Inserate")
                .withIcon(R.drawable.food)
                .withIdentifier(4);

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimaryDark)
                .withOnlyMainProfileImageVisible(true)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("John Doe")
                                .withEmail("john@doe.com")
                                .withIcon(R.drawable.profile)
                )
                .withTranslucentStatusBar(true)
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(item1, item2, item3, item4)
                .withSelectedItem(2)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(CreateActivity.this, MainActivity.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                Toast.makeText(getApplicationContext(),"Mein Profil kommt bald!",Toast.LENGTH_SHORT).show();
                            }
                            if (intent != null) {
                                CreateActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .build();

    }

}
