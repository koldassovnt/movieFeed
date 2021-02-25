package com.example.moviefeed.network

import com.example.moviefeed.model.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface Service {

    @GET(value = "trending/all/day?api_key=c844f3ac71e8a0a4754ba1aceae52800")
    fun getTrendingMovie(): Single<Movies>
}