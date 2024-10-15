package com.filmmash;

public class Movie {
    private int film_id;
    private String name;
    private String director;
    private int score;
    private String poster;

    public Movie() {
        this.name = "name";
        this.director = "director";
        this.score = 1400;
        this.poster = "https://gamestation.com.br/wp-content/themes/game-station/images/image-not-found.png";
    }

    public Movie(int film_id, String name, String director, int score, String poster) {
        this.film_id = film_id;
        this.name = name;
        this.director = director;
        this.score = score;
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
