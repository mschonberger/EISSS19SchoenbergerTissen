package com.example.scrap_o_food;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.scrap_o_food.adapter.RecyclerViewHomeAdapter;
import com.example.scrap_o_food.adapter.ViewPagerHeaderAdapter;
import com.example.scrap_o_food.model.Categories;
import com.example.scrap_o_food.model.Meals;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements HomeView {

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

        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withName("Home")
                .withBadge("19")
                .withIcon(R.drawable.ic_home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("Not Home").withBadge("12");
        SecondaryDrawerItem seconditem1 = new SecondaryDrawerItem().withName("Down here").withBadge("2");

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(android.R.color.holo_green_dark)
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
                .addDrawerItems(item1, item2, new DividerDrawerItem(), seconditem1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(MainActivity.this, "You pressed good!", Toast.LENGTH_SHORT).show();
                        return true;
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
    }

    @Override
    public void setCategory(List<Categories.Category> category) {
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorLoading(String message) {
        com.example.scrap_o_food.utils.Utils.showDialogMessage(this, "Title", message);
    }
}