package com.example.scrap_o_food.view.create;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scrap_o_food.R;
import com.example.scrap_o_food.model.Meals;
import com.example.scrap_o_food.model.Post;
import com.example.scrap_o_food.model.Posts;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import im.delight.android.location.SimpleLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    public static CreateClient createClient;
    private EditText edit_text_title;
    private EditText edit_text_description;
    private EditText editText_MHD;
    private Button button;
    private TextView viewDate;

    int idMeal;
    int idUser = 2;

    int idGroup = 1;
    String strCategory;
    String strStoring;
    String strCondition;
    String strMealThumb = "replaceMe";
    String strMail = "some2@ones.com";
    String strPhone = "987654321";

    private SimpleLocation location;
    double strLongitude;
    double strLatitude;
    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        edit_text_title = findViewById(R.id.edit_text_title);
        edit_text_description = findViewById(R.id.edit_text_description);
        editText_MHD = findViewById(R.id.editText_MHD);
        button = findViewById(R.id.button);

        getLocation();
        createDrawer();
        createKategorieSpinner();
        createLagerungSpinner();
        createZustandSpinner();
        checkBoxChecker();
        getCurrentDate();

        button.setOnClickListener(this);

        createClient = CreateClient.getInstance();


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

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimaryDark)
                .withOnlyMainProfileImageVisible(true)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("John Doe")
                                .withEmail("john@doe.com")
                                .withIcon(R.drawable.logo)
                )
                .withTranslucentStatusBar(true)
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .withActionBarDrawerToggle(false)
                .addDrawerItems(item1, item2)
                .withSelectedItem(2)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(CreateActivity.this, MainActivity.class);
                            } if (intent != null) {
                                CreateActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .build();

    }

    public void createKategorieSpinner() {

        Spinner kategorieSpinner = (Spinner) findViewById(R.id.KategorieSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Kategorien, android.R.layout.simple_spinner_dropdown_item);
        kategorieSpinner.setAdapter(adapter);
        kategorieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        strCategory = "Getraenke";
                        strMealThumb = "Getraenke";
                        break;
                    case 1:
                        strCategory = "Nudeln";
                        strMealThumb="Nudeln";
                        break;
                    case 2:
                        strCategory = "Backwaren";
                        strMealThumb="Backwaren";
                        break;
                    case 3:
                        strCategory = "Fleischwaren";
                        strMealThumb="Fleischwaren";
                        break;
                    case 4:
                        strCategory = "Obst";
                        strMealThumb= "Obst";
                        break;
                    case 5:
                        strCategory = "Gemuese";
                        strMealThumb = "Gemuese";
                        break;
                    case 6:
                        strCategory = "Mahlzeiten";
                        strMealThumb = "Mahlzeiten";
                        break;
                    case 7:
                        strCategory = "Snacks";
                        strMealThumb = "Snacks";
                        break;
                    case 8:
                        strCategory = "Suessigkeiten";
                        strMealThumb = "Suessigkeiten";
                        break;
                    case 9:
                        strCategory = "Milchprodukte";
                        strMealThumb = "Milchprodukte";
                        break;
                    case 10:
                        strCategory = "Gewuerze";
                        strMealThumb = "Gewuerze";
                        break;
                    case 11:
                        strCategory = "Sonstiges";
                        strMealThumb = "Sonstiges";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void createLagerungSpinner() {

        Spinner lagerungSpinner = (Spinner) findViewById(R.id.LagerungSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Lagerung, android.R.layout.simple_spinner_dropdown_item);
        lagerungSpinner.setAdapter(adapter);
        lagerungSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        strStoring = "open";
                        break;
                    case 1:
                        strStoring = "fridge";
                        break;
                    case 2:
                        strStoring = "protected";
                        break;
                    case 3:
                        strStoring = "freezer";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void createZustandSpinner() {

        Spinner zustandSpinner = (Spinner) findViewById(R.id.ZustandSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Zustand, android.R.layout.simple_spinner_dropdown_item);
        zustandSpinner.setAdapter(adapter);
        zustandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        strCondition = "open";
                        break;
                    case 1:
                        strCondition = "closed";
                        break;
                    case 2:
                        strCondition = "fresh";
                        break;
                    case 3:
                        strCondition = "frozen";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void checkBoxChecker() {

        CheckBox groupCheck = (CheckBox) findViewById(R.id.checkBox);

        groupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (groupCheck.isChecked()){
                    Toast.makeText(getApplicationContext(), "Anzeige wird privat erstellt!", Toast.LENGTH_SHORT).show();
                    idGroup = 2;
                } else if (!groupCheck.isChecked()){
                    Toast.makeText(getApplicationContext(), "Anzeige wird öffentlich erstellt!", Toast.LENGTH_SHORT).show();
                    idGroup = 1;
                }
            }
        });
    }

    public void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        viewDate = findViewById(R.id.currentDate);
        viewDate.setText(currentDate);
    }

    public void getLocation() {
        location = new SimpleLocation(this);

        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }

        strLatitude = location.getLatitude();
        strLongitude = location.getLongitude();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Post post = new Post(String.valueOf(idMeal), String.valueOf(idUser), String.valueOf(idGroup), edit_text_title.getText().toString(),
                        edit_text_description.getText().toString(), String.valueOf(strLatitude), currentDate, String.valueOf(strLongitude), strMealThumb, strCategory, strStoring, strCondition, editText_MHD.getText().toString(), strMail, strPhone);
                CreateActivity.createClient.saveMeal(post, new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        Post responseAd = response.body();

                        if (response.isSuccessful() && responseAd != null) {
                            Toast.makeText(CreateActivity.this, "Inserat erfolgreich erstellt",
                                    Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Toast.makeText(CreateActivity.this,
                                    String.format("Response ist %s", String.valueOf(response.code()))
                                    , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Toast.makeText(CreateActivity.this,"Error is " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                break;
        }
    }
}
