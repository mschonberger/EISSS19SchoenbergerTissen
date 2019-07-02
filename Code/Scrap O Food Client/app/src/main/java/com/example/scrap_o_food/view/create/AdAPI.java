package com.example.scrap_o_food.view.create;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdAPI {

    @GET("posts")
    Call<List<Post>> getPosts();

}
