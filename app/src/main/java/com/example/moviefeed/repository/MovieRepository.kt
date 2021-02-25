package com.example.moviefeed.repository

import com.example.moviefeed.model.Movies
import com.example.moviefeed.network.Service
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: Service
){
    fun getTrendingMovie(): Single<Movies> {
        return movieService.getTrendingMovie()
            .subscribeOn(Schedulers.io())
    }
}