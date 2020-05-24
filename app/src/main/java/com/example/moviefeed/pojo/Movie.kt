package com.example.moviefeed.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviefeed.api.ApiFactory.BASE_IMAGE_URL
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "now_playing_movies")
data class Movie (
    @SerializedName("poster_path")
    @Expose
    val posterPath: String?,
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("original_title")
    @Expose
    val originalTitle: String?,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double?,
    @SerializedName("overview")
    @Expose
    val overview: String?,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String?
) {
    fun getFullPosterUrl(): String {
        return BASE_IMAGE_URL + posterPath
    }
}