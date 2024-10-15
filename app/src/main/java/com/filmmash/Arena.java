package com.filmmash;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Arena {
    private Movie movie1;
    private Movie movie2;
    private int winner;

    public Arena(Movie movie1, Movie movie2){
        this.movie1 = movie1;
        this.movie2 = movie2;
        this.winner = 0;
    }

    public Arena(){
        this.movie1 = new Movie();
        this.movie2 = new Movie();
    }

    public void jsonToArena (String jsonString){
        Gson gson = new Gson();
        MovieResponse movieResponse = gson.fromJson(jsonString, MovieResponse.class);
        if (movieResponse == null) {
            System.out.println("No answer was received from server");
        }else{
            this.movie1 = movieResponse.getMovie1();
            this.movie2 = movieResponse.getMovie2();
        }
    }

    public ArrayList<Movie> getMoviesList(){
        ArrayList<Movie> list = new ArrayList<>();
        list.add(this.movie1);
        list.add(this.movie2);
        return list;
    }

    public Movie getMovie1() {
        return movie1;
    }

    public void setMovie1(Movie movie1) {
        this.movie1 = movie1;
    }

    public Movie getMovie2() {
        return movie2;
    }

    public void setMovie2(Movie movie2) {
        this.movie2 = movie2;
    }

    public int getWinner(){
        return this.winner;
    }

    public void setWinner(int winner){
        this.winner = winner;
    }
}
