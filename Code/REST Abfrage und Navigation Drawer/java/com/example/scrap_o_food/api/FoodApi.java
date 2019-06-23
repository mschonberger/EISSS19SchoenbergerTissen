package com.example.scrap_o_food.api;

import com.example.scrap_o_food.model.Categories;
import com.example.scrap_o_food.model.Meals;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApi {

    @GET("latest.php")
    Call<Meals> getMeal();

    @GET("categories.php")
    Call<Categories> getCategories();
}
