package com.example.moviefeed.data.api

import com.example.moviefeed.data.pojo.MovieDetailRemote
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiFactory {

    // https://api.themoviedb.org/3/movie/popular?api_key=c844f3ac71e8a0a4754ba1aceae52800&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=c844f3ac71e8a0a4754ba1aceae52800
    // https://api.themoviedb.org/3/


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetailRemote>
}