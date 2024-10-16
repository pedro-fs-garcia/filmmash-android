package com.filmmash;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

public class ApiService {

    private static final String BASE_URL = "http://10.0.2.2:5000";

    public interface JsonResponseCallback {
        void onResponse(String jsonResponse);
    }

    public void getJsonResponse(String endPoint, JsonResponseCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            String jsonResponse = "";
            try {
                URL url = new URL(BASE_URL + endPoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    jsonResponse = response.toString();
                } else {
                    System.out.println("GET request did not work");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Executa o callback passando a resposta JSON
            callback.onResponse(jsonResponse);
        });
    }

    public void getNewArena(JsonResponseCallback callback) {
        getJsonResponse("/get_arena_json", jsonResponse -> {
            callback.onResponse(jsonResponse);
        });
    }

    public void getAllRatings(JsonResponseCallback callback){
        getJsonResponse("/get_all_ratings", jsonResponse -> {
           callback.onResponse((jsonResponse));
        });
    }
}