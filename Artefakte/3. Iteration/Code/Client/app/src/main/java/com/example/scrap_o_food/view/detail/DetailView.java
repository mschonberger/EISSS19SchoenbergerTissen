package com.example.scrap_o_food.view.detail;

import com.example.scrap_o_food.model.Meals;

public interface DetailView {

    void showLoading();
    void hideLoading();
    void setMeal(Meals.Meal meal);
    void onErrorLoading(String Message);

}
