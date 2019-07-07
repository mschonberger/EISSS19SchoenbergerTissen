package com.example.scrap_o_food.view.detail;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.scrap_o_food.R;
import com.example.scrap_o_food.model.Meals;
import com.example.scrap_o_food.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.scrap_o_food.view.home.MainActivity.EXTRA_DETAIL;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.mealThumb)
    ImageView mealThumb;

    @BindView(R.id.category)
    TextView category;

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.instructions)
    TextView instructions;

    @BindView(R.id.ingredient)
    TextView ingredients;

    @BindView(R.id.measure)
    TextView measures;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.message)
    TextView message;

    @BindView(R.id.phone)
    TextView phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setupActionBar();

        Intent intent = getIntent();
        String mealName = intent.getStringExtra(EXTRA_DETAIL);

        DetailPresenter presenter = new DetailPresenter(this);
        presenter.getMealById(mealName);

    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorWhite));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setMeal(Meals.Meal meal) {

        Picasso.get().load(meal.getStrMealThumb()).into(mealThumb);
        collapsingToolbarLayout.setTitle(meal.getStrMeal());
        category.setText(meal.getStrCategory());
        date.setText(meal.getAdDate());
        instructions.setText(meal.getStrDescription());
        setupActionBar();

        if (!meal.getStrStoring().isEmpty()) {
            ingredients.append("\n \u2022 Lagerung ");
        }
        if (!meal.getStrCondition().isEmpty()) {
            ingredients.append("\n \u2022 Zustand ");
        }
        if (!meal.getStrMHD().isEmpty()) {
            ingredients.append("\n \u2022 MHD ");
        }

        if (!meal.getStrStoring().isEmpty() && !Character.isWhitespace(meal.getStrStoring().charAt(0))){
            measures.append("\n : " + meal.getStrStoring());
        }
        if (!meal.getStrCondition().isEmpty() && !Character.isWhitespace(meal.getStrCondition().charAt(0))){
            measures.append("\n : " + meal.getStrCondition());
        }
        if (!meal.getStrMHD().isEmpty() && !Character.isWhitespace(meal.getStrMHD().charAt(0))){
            measures.append("\n : " + meal.getStrMHD());
        }




        message.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, meal.getStrMail());
            intent.putExtra(Intent.EXTRA_SUBJECT, "Anfrage zu ihrem Inserat:" + meal.getStrMeal());
            intent.putExtra(Intent.EXTRA_TEXT, "mail body");
            startActivity(Intent.createChooser(intent, ""));
        });

        phone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + meal.getStrPhone()));
            startActivity(intent);
        });

    }

    @Override
    public void onErrorLoading(String Message) {
        Utils.showDialogMessage(this, "Error", Message);
    }
}