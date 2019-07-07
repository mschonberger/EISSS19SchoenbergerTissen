package com.example.scrap_o_food.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts implements Serializable
{

    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;
    private final static long serialVersionUID = 2813193536442083843L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Posts() {
    }

    /**
     *
     * @param posts
     */
    public Posts(List<Post> posts) {
        super();
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}