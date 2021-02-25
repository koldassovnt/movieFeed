package com.example.moviefeed.ui.overview

import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviefeed.model.Movie
import com.example.moviefeed.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class OverviewViewModel @ViewModelInject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val _trendingMovies = MutableLiveData<List<Movie>>()
    val trendingMovies: LiveData<List<Movie>>
        get() = _trendingMovies

    init {
        movieRepository.getTrendingMovie()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                _trendingMovies.apply { value = response.results }
            }, {error ->
                Timber.e(error)
            })
    }
}