package com.raghav.raghavstudentgiri.retrofit;

import com.raghav.raghavstudentgiri.model.Todo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class TodoApi {
    private static final String url = "https://jsonplaceholder.typicode.com/users/1/todos/";

    public static PostService postService = null;

    public static PostService getService() {
        if (postService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postService = retrofit.create(PostService.class);
        }
        return postService;
    }

    public interface PostService {

        @GET(".")
        Call<ArrayList<Todo>> getTodoList();

        @GET("{id}")
        Call<Todo> getTodoItem(@Path("id") int id);

        @POST(".")
        Call<Todo> addTodoItem(@Body Todo item);

        @DELETE("{id}")
        Call<Void> deleteItem(@Path("id") int id);

        @PUT(".")
        Call<Todo> updateItem(@Body Todo item);
    }
}
