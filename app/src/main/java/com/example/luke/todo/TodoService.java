package com.example.luke.todo;

/**
 * Created by luke on 06/10/17.
 */

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface TodoService {

    @GET("/todo")
    public void getTodo(Callback<List<Todo>> callback);

    @GET("/todo/{id}")
    public void getTodoById(@Path("id") Integer id, Callback<Todo> callback);

    @PUT("/todo{id}")
    public void updateTodobyId(@Path("id") Integer id, @Body Todo todo, Callback<Todo> callback);

    @DELETE("/todo{id}")
    public void deleteTodoById(@Path("id") Integer id, Callback<Todo> callback);

    @POST("/todo")
    public void addTodoItem(@Body Todo todo, Callback<Todo> callback);

}
