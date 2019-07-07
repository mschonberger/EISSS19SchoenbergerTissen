package com.example.scrap_o_food.view.category;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import com.example.scrap_o_food.R;
import com.example.scrap_o_food.adapter.RecyclerViewMealByCategory;
import com.example.scrap_o_food.model.Meals;
import com.example.scrap_o_food.utils.Utils;

import com.example.scrap_o_food.view.create.CreateActivity;
import com.example.scrap_o_food.view.detail.DetailActivity;
import com.example.scrap_o_food.view.home.MainActivity;
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.delight.android.location.SimpleLocation;

import static com.example.scrap_o_food.view.home.MainActivity.EXTRA_DETAIL;

public class CategoryFragment extends Fragment implements CategoryView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imageCategory)
    ImageView imageCategory;
    @BindView(R.id.imageCategoryBg)
    ImageView imageCategoryBg;
    @BindView(R.id.textCategory)
    TextView textCategory;

    AlertDialog.Builder descDialog;

    double longitude;
    double latitude;
    double temperature;
    String wetter;
    String city;
    boolean weatherWasChecked = false;

    private SimpleLocation location;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        weatherCheck();

        if(getArguments() != null) {
            textCategory.setText(getArguments().getString("EXTRA_DATA_DESC"));
            Picasso.get()
                    .load(getArguments().getString("EXTRA_DATA_IMAGE"))
                    .into(imageCategory);
            Picasso.get()
                    .load(getArguments().getString("EXTRA_DATA_IMAGE"))
                    .into(imageCategoryBg);
            descDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(getArguments().getString("EXTRA_DATA_NAME"))
                    .setMessage(getArguments().getString("EXTRA_DATA_DESC"));

            CategoryPresenter presenter = new CategoryPresenter(this);

            if(!weatherWasChecked) {
                presenter.getMealByCategory(getArguments().getString("EXTRA_DATA_NAME"));
            } /*else if (weatherWasChecked) {
                getActivity().recreate();
                presenter.getMealByCategory(getArguments().getString("EXTRA_DATA_NAME"), latitude, longitude);
            }*/

        }

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMeals(List<Meals.Meal> meals) {
        RecyclerViewMealByCategory adapter = new RecyclerViewMealByCategory(getActivity(), meals);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setClipToPadding(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new RecyclerViewMealByCategory.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView mealName = view.findViewById(R.id.mealName);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Error ", message);
    }

    @OnClick(R.id.cardCategory)
    public void onClick() {
        descDialog.setPositiveButton("Schließen", ((dialog, which) -> dialog.dismiss()));
        descDialog.show();
    }

    public boolean weatherCheck() {

        OpenWeatherMapHelper weather = new OpenWeatherMapHelper("393378060ce02eeac4311440542c2c08");

        location = new SimpleLocation(getContext());

        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(getContext());
        }

        latitude = 51.028351;
        longitude = 7.565430;

        weather.setUnits(Units.METRIC);
        weather.setLang(Lang.GERMAN);

        weather.getCurrentWeatherByGeoCoordinates(latitude, longitude, new CurrentWeatherCallback() {

            @Override
            public void onSuccess(CurrentWeather currentWeather) {

                ImageView image = new ImageView(getContext());

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

                if (temperature < 2.00 || temperature > 30.00 || wetter.equals("Gewitter")) {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getContext())
                                    .setView(image)
                                    .setTitle("Wetterwarnung!")
                                    .setMessage("Temperatur : " + temperature + "°C" +
                                            "\nBeschreibung: " + wetter +
                                            "\n\nStadt : " + city +
                                            "\nLatitude : " + latitude +
                                            "\nLongitude : " + longitude +
                                            "\n\n\nSoll die Suche angepasst werden?\n\n")
                                    .setPositiveButton("Suche anpassen", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            weatherWasChecked=true;
                                        }
                                    })
                                    .setNegativeButton("Ignorieren              ", new DialogInterface.OnClickListener() {
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

        return weatherWasChecked;

    }

}