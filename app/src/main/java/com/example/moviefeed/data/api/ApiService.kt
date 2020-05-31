package com.example.moviefeed.data.api

import com.example.moviefeed.data.pojo.MovieDetails
import com.example.moviefeed.data.pojo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}