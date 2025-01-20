package com.filmmash;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class RatingList {
    private Movie[] movieList;

    public RatingList(Movie[] movieList){
        this.movieList = movieList;
    }

    public RatingList(){
        this.movieList = null;
    }

    public void jsonToRatingList(String jsonString) {
        System.out.println(jsonString);
        System.out.println("Initializing conversion from json to RatingList");

        if (jsonString == null || jsonString.trim().isEmpty()) {
            System.out.println("Error: jsonString is null or empty.");
            this.movieList = null;
            return;
        } else{
            try {
                Gson gson = new Gson();

                // Usar TypeToken para especificar o tipo correto do HashMap
                Type type = new TypeToken<HashMap<String, HashMap<String, Object>>>(){}.getType();
                HashMap<String, HashMap<String, Object>> map = gson.fromJson(jsonString, type);

                Movie[] movieVector = new Movie[map.size()];

                for (Map.Entry<String, HashMap<String, Object>> set : map.entrySet()) {
                    // Converter a chave de String para Integer
                    int position = Integer.parseInt(set.getKey());

                    HashMap<String, Object> data = set.getValue();
                    Movie newMovie = new Movie();
                    newMovie.setFilm_id(position);

                    // Preencher os dados do filme usando o método setAttribute
                    for (Map.Entry<String, Object> movieData : data.entrySet()) {
                        newMovie.setAttribute(movieData.getKey(), movieData.getValue());
                    }
                    // Atribuir o novo filme à posição correta no vetor
                    movieVector[position-1] = newMovie;
                }
                this.movieList = movieVector; // Atribuir a lista finalizada à variável movieList

            } catch (JsonSyntaxException e) {
                System.out.println("Error: Failed to parse JSON - " + e.getMessage());
                this.movieList = null;
            } catch (NumberFormatException e) {
                System.out.println("Error: Unexpected exception - " + e.getMessage());
                this.movieList = null;
            }
        }

    }

    public void setMovieList(Movie[] movieList){
        this.movieList = movieList;
    }

    public Movie[] getMovieList(){
        return this.movieList;
    }
}
