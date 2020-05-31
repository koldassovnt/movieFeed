package com.example.moviefeed.ui.scenes.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviefeed.data.api.ApiService
import com.example.moviefeed.data.api.POST_PER_PAGE
import com.example.moviefeed.data.pojo.PopularMovies
import com.example.moviefeed.data.repository.MoviesDataSource
import com.example.moviefeed.data.repository.MoviesDataSourceFactory
import com.example.moviefeed.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesPagedListRepository (private val apiService : ApiService)  {
    lateinit var moviePagedList: LiveData<PagedList<PopularMovies>>
    lateinit var moviesDataSourceFactory: MoviesDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<PopularMovies>> {
        moviesDataSourceFactory = MoviesDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MoviesDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MoviesDataSource::networkState)
    }
}