package com.example.moviebuff.Networking;

import com.example.moviebuff.ModelClass.TMDB;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMovieDataInterface {
    @GET("discover/movie?api_key=105d5402b9979cd998437c33b61b0797&region=IN")
    Call<TMDB> getMovieData(@Query("sort_by") String sort_by);
}
