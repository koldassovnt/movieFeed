package com.example.moviefeed.ui.scenes.movie

import androidx.lifecycle.LiveData
import com.example.moviefeed.data.api.ApiService
import com.example.moviefeed.data.pojo.MovieDetails
import com.example.moviefeed.data.repository.MovieDetailsNetworkDataSource
import com.example.moviefeed.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailRepository(private val apiService: ApiService) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.movieDetails
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }

}