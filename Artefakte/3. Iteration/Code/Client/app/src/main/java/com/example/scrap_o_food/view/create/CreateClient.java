package com.example.scrap_o_food.view.create;

import com.example.scrap_o_food.api.FoodApi;
import com.example.scrap_o_food.api.FoodClient;
import com.example.scrap_o_food.model.Meals;
import com.example.scrap_o_food.model.Post;
import com.example.scrap_o_food.model.Posts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateClient {

        private static FoodApi service;
        private static CreateClient createClient;

        private CreateClient() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.178.20:1337/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(FoodApi.class);
        }

        public static CreateClient getInstance() {
            if (createClient == null) {
                createClient = new CreateClient();
            }
            return createClient;
        }

        public void saveMeal (Post post, Callback<Post> callback) {
            Call<Post> postCall = service.saveMeal(post);
            postCall.enqueue(callback);
        }

        /*public void saveMeal(Meals meals, Callback<Meals> callback) {
            Call<Meals> mealsCall = service.saveMeal(meals);
            mealsCall.enqueue(callback);
        }*/

}
