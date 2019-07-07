package com.example.scrap_o_food.view.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

        createDrawer();

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
                } else if (wetter.equals("Gewitter")) {
                    image.setImageResource(R.drawable.weather_thunder);
                } else if (wetter.equals("Schnee") || temperature < 5.00) {
                    image.setImageResource(R.drawable.weather_cold);
                } else if (temperature > 30.00){
                    image.setImageResource(R.drawable.weather_hot);
                } else if (wetter.equals("Bewölkt") || wetter.equals("Bedeckt") || wetter.equals("Überwiegend bewölkt")) {
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
        SecondaryDrawerItem sitem1 = new SecondaryDrawerItem()
                .withName("Wetter überprüfen")
                .withIcon(R.drawable.ic_sun)
                .withIdentifier(3);

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
                .addDrawerItems(item1,
                        item2,
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
        for (Meals.Meal mealResult: meal) {
            Log.w("meal name: ", mealResult.getStrMeal());
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