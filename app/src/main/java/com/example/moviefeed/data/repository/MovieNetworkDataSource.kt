package com.example.moviefeed.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviefeed.data.api.ApiService
import com.example.moviefeed.data.pojo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieNetworkDataSource(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails>
        get() = _movieDetails

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _movieDetails.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message)
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("MovieDetailsDataSource", e.message)
        }


    }
}