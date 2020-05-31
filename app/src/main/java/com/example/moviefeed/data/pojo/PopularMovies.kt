package com.example.moviefeed.data.pojo


import com.google.gson.annotations.SerializedName

data class PopularMovies(
    @SerializedName("vote_average")
    val voteAverage: Double,
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String
)