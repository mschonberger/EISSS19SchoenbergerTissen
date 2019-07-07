package com.example.scrap_o_food.api;

import com.example.scrap_o_food.model.Categories;
import com.example.scrap_o_food.model.Meals;
import com.example.scrap_o_food.model.Post;
import com.example.scrap_o_food.model.Posts;

import org.w3c.dom.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodApi {

    @GET("categories")
    Call<Categories> getCategories();

    @GET("meals")
    Call<Meals> getMealByCategory(@Query("c") String category,
                                  @Query("lat") double lat,
                                  @Query( "long") double lon);

    @GET("meals")
    Call<Meals> getMealByCategory(@Query("strCategory") String category,
                                  @Query("idGroup") int idGroup);

    @GET("meals")
    Call<Meals> getMeal(@Query("idGroup") int idGroup);

    @GET("meals")
    Call<Meals> getMealByName(@Query("strMeal") String strMeal);

    @POST("meals")
    Call<Post> saveMeal(@Body Post post);

}