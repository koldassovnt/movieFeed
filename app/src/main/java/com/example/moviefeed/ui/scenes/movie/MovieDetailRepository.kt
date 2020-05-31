package com.example.moviefeed.ui.scenes.movie

import androidx.lifecycle.LiveData
import com.example.moviefeed.data.api.ApiService
import com.example.moviefeed.data.pojo.MovieDetails
import com.example.moviefeed.data.repository.MovieNetworkDataSource
import com.example.moviefeed.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailRepository(private val apiService: ApiService) {
    lateinit var movieNetworkDataSource: MovieNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieNetworkDataSource = MovieNetworkDataSource(apiService,compositeDisposable)
        movieNetworkDataSource.fetchMovieDetails(movieId)

        return movieNetworkDataSource.movieDetails
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieNetworkDataSource.networkState
    }

}