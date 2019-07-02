package com.example.scrap_o_food.view.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scrap_o_food.R;
import com.example.scrap_o_food.adapter.RecyclerViewHomeAdapter;
import com.example.scrap_o_food.adapter.ViewPagerHeaderAdapter;
import com.example.scrap_o_food.model.Categories;
import com.example.scrap_o_food.model.Meals;
import com.example.scrap_o_food.view.category.CategoryActivity;
import com.example.scrap_o_food.view.create.CreateActivity;
import com.example.scrap_o_food.view.detail.DetailActivity;
import com.example.scrap_o_food.utils.GPSTracker;
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.common.Main;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.mikepenz.iconics.typeface.GenericFont;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.icons.MaterialDrawerFont;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.delight.android.location.SimpleLocation;

public class MainActivity extends AppCompatActivity implements HomeView {

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DETAIL = "detail";

    double longitude;
    double latitude;
    double temperature;
    String wetter;
    String city;

    private SimpleLocation location;

    @BindView(R.id.viewPagerHeader) ViewPager viewPagerMeal;
    @BindView(R.id.recyclerCategory) RecyclerView recyclerViewCategory;

    HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();

        weatherCheck();

        createDrawer();

    }

    public void weatherCheck() {

        OpenWeatherMapHelper weather = new OpenWeatherMapHelper("393378060ce02eeac4311440542c2c08");

        location = new SimpleLocation(this);

        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        weather.setUnits(Units.METRIC);
        weather.setLang(Lang.GERMAN);

        weather.getCurrentWeatherByGeoCoordinates(latitude, longitude, new CurrentWeatherCallback() {

            @Override
            public void onSuccess(CurrentWeather currentWeather) {

                ImageView image = new ImageView(MainActivity.this);

                temperature = (double) currentWeather.getMain().getTempMax();
                city = currentWeather.getName();
                wetter = currentWeather.getWeather().get(0).getDescription();

                if (wetter.equals("Regen") || wetter.equals("Leichter Regen") || wetter.equals("Regenschauer")) {
                    image.setImageResource(R.drawable.weather_rain);
                } else if (wetter == "Gewitter") {
                    image.setImageResource(R.drawable.weather_thunder);
                } else if (wetter == "Schnee") {
                    image.setImageResource(R.drawable.weather_cold);
                } else if (temperature > 30.00){
                    image.setImageResource(R.drawable.weather_hot);
                } else if (wetter.equals("Bewölkt") || wetter.equals("Überwiegend bewölkt")) {
                    image.setImageResource(R.drawable.weather_cloud);
                } else {
                    image.setImageResource(R.drawable.ic_sun);
                }

                if (temperature > 30.00) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(MainActivity.this)
                                    .setView(image)
                                    .setTitle("Wetterwarnung!")
                                    .setMessage("Temperatur : " + temperature + "°C" +
                                            "\nBeschreibung: " + wetter +
                                            "\n\nStadt : " + city +
                                            "\nLatitude : " + latitude +
                                            "\nLongitude : " + longitude +
                                            "\n\n\n")
                                    .setPositiveButton("Verstanden", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                    builder.create().show();
                }

                if (temperature < 5.00) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(MainActivity.this)
                                    .setView(image)
                                    .setTitle("Wetterwarnung!")
                                    .setMessage("Temperatur : " + temperature + "°C" +
                                            "\nBeschreibung: " + wetter +
                                            "\n\nStadt : " + city +
                                            "\nLatitude : " + latitude +
                                            "\nLongitude : " + longitude +
                                            "\n\n\n")
                                    .setPositiveButton("Verstanden", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                    builder.create().show();
                }

                if (wetter.equals("Gewitter")) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(MainActivity.this)
                                    .setView(image)
                                    .setTitle("Wetterwarnung!")
                                    .setMessage("Temperatur : " + temperature + "°C" +
                                            "\nBeschreibung: " + wetter +
                                            "\n\nStadt : " + city +
                                            "\nLatitude : " + latitude +
                                            "\nLongitude : " + longitude +
                                            "\n\n\n")
                                    .setPositiveButton("Verstanden", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                    builder.create().show();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
            }

    });

    }

    public void weatherCheckByUser() {
        OpenWeatherMapHelper weather = new OpenWeatherMapHelper("393378060ce02eeac4311440542c2c08");

        location = new SimpleLocation(this);

        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(this);
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        weather.setUnits(Units.METRIC);
        weather.setLang(Lang.GERMAN);

        weather.getCurrentWeatherByGeoCoordinates(latitude, longitude, new CurrentWeatherCallback() {

            @Override
            public void onSuccess(CurrentWeather currentWeather) {

                ImageView image = new ImageView(MainActivity.this);

                temperature = (double) currentWeather.getMain().getTempMax();
                city = currentWeather.getName();
                wetter = currentWeather.getWeather().get(0).getDescription();

                if (wetter.equals("Regen") || wetter.equals("Leichter Regen") || wetter.equals("Regenschauer")) {
                    image.setImageResource(R.drawable.weather_rain);
                } else if (wetter == "Gewitter") {
                    image.setImageResource(R.drawable.weather_thunder);
                } else if (wetter == "Schnee") {
                    image.setImageResource(R.drawable.weather_cold);
                } else if (temperature > 30.00){
                    image.setImageResource(R.drawable.weather_hot);
                } else if (wetter.equals("Bewölkt")|| wetter.equals("Überwiegend bewölkt")) {
                    image.setImageResource(R.drawable.weather_cloud);
                } else {
                    image.setImageResource(R.drawable.ic_sun);
                }

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this)
                                .setView(image)
                                .setTitle("Aktuelles Wetter")
                                .setMessage("Temperatur : " + temperature + "°C" +
                                        "\nBeschreibung: " + wetter +
                                        "\n\nStadt : " + city +
                                        "\nLatitude : " + latitude +
                                        "\nLongitude : " + longitude +
                                        "\n\n\n")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                    builder.create().show();
                }

            @Override
            public void onFailure(Throwable throwable) {
            }

        });
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
        SecondaryDrawerItem sitem1 = new SecondaryDrawerItem()
                .withName("Wetter überprüfen")
                .withIcon(R.drawable.ic_sun)
                .withIdentifier(5);

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
                .addDrawerItems(item1,
                        item2,
                        item3,
                        item4,
                        new SecondaryDrawerItem().withName("Features"),
                        sitem1
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(MainActivity.this, CreateActivity.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                Toast.makeText(getApplicationContext(),"Mein Profil kommt bald!",Toast.LENGTH_SHORT).show();
                            } else if (drawerItem.getIdentifier() == 4) {
                                Toast.makeText(getApplicationContext(),"Meine Inserate kommt bald!",Toast.LENGTH_SHORT).show();
                            } else if (drawerItem.getIdentifier() == 5) {
                                weatherCheckByUser();
                            }
                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .build();

    }

    @Override
    public void showLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.INVISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.INVISIBLE);
    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {
        for (Meals.Meal mealresult: meal) {
            Log.w("meal name: ", mealresult.getStrMeal());
        }

        ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meal, this);
        viewPagerMeal.setAdapter(headerAdapter);
        viewPagerMeal.setPadding(20,0,150,0);
        headerAdapter.notifyDataSetChanged();

        headerAdapter.setOnItemClickListener((view, position) -> {
            TextView mealName = view.findViewById(R.id.mealName);
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    public void setCategory(List<Categories.Category> category) {
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();

        homeAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
            intent.putExtra(EXTRA_POSITION, position);
            startActivity(intent);
        });
    }

    @Override
    public void onErrorLoading(String message) {
        com.example.scrap_o_food.utils.Utils.showDialogMessage(this, "Title", message);
    }

}