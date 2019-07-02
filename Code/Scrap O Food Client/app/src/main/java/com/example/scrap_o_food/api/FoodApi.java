package com.example.scrap_o_food.api;

import com.example.scrap_o_food.model.Categories;
import com.example.scrap_o_food.model.Meals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApi {

    @GET("latest.php")
    Call<Meals> getMeal();

    @GET("categories.php")
    Call<Categories> getCategories();

    @GET("filter.php")
    Call<Meals> getMealByCategory(@Query("c") String category);

    @GET("search.php")
    Call<Meals> getMealByName(@Query("s") String mealName);

    @GET("filter.php")
    Call<Meals> getMealByArea(@Query("a") String area);

}
