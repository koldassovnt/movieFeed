package com.example.moviefeed.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ListOfMovies {
    @SerializedName("results")
    @Expose
    val results: List<Movie>? = null
}