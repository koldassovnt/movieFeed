package com.example.moviefeed.data.pojo


import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val budget: Int,
    val id: Int,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)