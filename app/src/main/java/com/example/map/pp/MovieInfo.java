package com.example.map.pp;

public class MovieInfo {
    private String title;
    private String original_title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private String vote_average;
    private String id;
    public MovieInfo(){

    }
    public MovieInfo(String title, String original_title, String poster_path, String overview,
                     String backdrop_path, String release_date, String vote_average, String id){
        this.title = title;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average=vote_average;
        this.id=id;
    }

    public String getTitle(){
        return title;
    }

    public String getOriginal_title(){
        return original_title;
    }

    public String getPoster_path(){
        return poster_path;
    }

    public String getOverview(){
        return overview;
    }

    public String getBackdrop_path(){
        return backdrop_path;
    }

    public String getRelease_date(){
        return release_date;
    }

    public String getVote_average(){
        return vote_average;
    }

    public String getId(){return id;}

}
