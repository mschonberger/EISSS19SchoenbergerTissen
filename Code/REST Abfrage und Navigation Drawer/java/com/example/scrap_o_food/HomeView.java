package com.example.scrap_o_food;

import com.example.scrap_o_food.model.Meals;
import com.example.scrap_o_food.model.Categories;

import java.util.List;

public interface HomeView {

    void showLoading();
    void hideLoading();
    void setMeal(List<Meals.Meal> meal);
    void setCategory(List<Categories.Category> category);
    void onErrorLoading(String message);
}
