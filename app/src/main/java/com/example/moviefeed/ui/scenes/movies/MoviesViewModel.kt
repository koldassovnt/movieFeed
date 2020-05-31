package com.example.moviefeed.ui.scenes.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviefeed.data.pojo.PopularMovies
import com.example.moviefeed.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesViewModel(private val moviesRepository: MoviesPagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<PopularMovies>> by lazy {
        moviesRepository.fetchLiveMoviePagedList(compositeDisposable)
    }
    val networkState: LiveData<NetworkState> by lazy {
        moviesRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}