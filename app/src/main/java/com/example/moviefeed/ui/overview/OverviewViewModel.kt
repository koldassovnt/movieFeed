package com.example.moviefeed.ui.overview

import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviefeed.model.Movie
import com.example.moviefeed.model.Resource
import com.example.moviefeed.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

class OverviewViewModel @ViewModelInject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _trendingMovies = MutableLiveData<Resource<List<Movie>>>()
    val trendingMovies: LiveData<Resource<List<Movie>>>
        get() = _trendingMovies

    init {
        compositeDisposable.add(
            movieRepository.getTrendingMovie()
                .doOnSubscribe  {_trendingMovies.value = Resource.Loading(null) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {response -> _trendingMovies.apply { value = Resource.Success(response.results) } },
                    {error ->
                        Timber.e(error)
                        _trendingMovies.value = Resource.Error(error.message!!, null)
                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear() // to avoid memory leak
    }
}