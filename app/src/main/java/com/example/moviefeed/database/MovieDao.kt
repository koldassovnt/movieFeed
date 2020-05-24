package com.example.moviefeed.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviefeed.pojo.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM now_playing_movies ORDER BY releaseDate DESC")
    fun getMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM now_playing_movies WHERE originalTitle == :movieName LIMIT 1")
    fun getMovieDetailInfo(movieName: String) : LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<Movie>)


}