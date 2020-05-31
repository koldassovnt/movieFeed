package com.example.moviefeed.data.repository

import com.example.moviefeed.data.api.ApiService
import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviefeed.data.pojo.PopularMovies

class MoviesDataSourceFactory (private val apiService : ApiService, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, PopularMovies>() {

    val moviesLiveDataSource =  MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, PopularMovies> {
        val movieDataSource = MoviesDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}