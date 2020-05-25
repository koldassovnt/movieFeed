package com.example.moviefeed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.moviefeed.api.ApiFactory
import com.example.moviefeed.api.ApiService
import com.example.moviefeed.database.AppDatabase
import com.example.moviefeed.pojo.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MovieViewModel(application: Application) : AndroidViewModel(application)  {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    fun getDetailInfo(mName: String): LiveData<Movie> {
        return db.movieDao().getMovieDetailInfo(mName)
    }

    val movieList = db.movieDao().getMovies()

    fun loadData() {
        val disposable = ApiFactory.apiService.getNowPlayingMovies()
            .map { it.results }
            .delaySubscription(20, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                it?.let { it1 -> db.movieDao().insertMovie(it1) }
                Log.d("TEST_OF_LOADING_DATA", "Success: $it")
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failure: ${it.message}")
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}