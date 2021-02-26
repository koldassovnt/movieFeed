package com.example.moviefeed.network

import com.example.moviefeed.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface Service {

    @GET(value = "trending/all/day")
    fun getTrendingMovie(): Single<Movies>
}