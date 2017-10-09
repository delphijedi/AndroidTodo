package com.example.luke.todo;

import retrofit.RestAdapter;

/**
 * Created by luke on 06/10/17.
 */

public class RestService {

    private static final String URL = "http://10.0.2.2:5000/api";
    private retrofit.RestAdapter restAdapter;
    private TodoService todoService;

    public RestService()
    {
        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        todoService = restAdapter.create(TodoService.class);
    }

    public TodoService getService()
    {
        return todoService;
    }
}
