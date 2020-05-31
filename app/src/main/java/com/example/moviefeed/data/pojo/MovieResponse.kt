package com.example.moviefeed.data.pojo


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    @SerializedName("results")
    val popularMovies: List<PopularMovies>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)