package com.example.moviefeed.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviefeed.data.api.ApiService
import com.example.moviefeed.data.api.FIRST_PAGE
import com.example.moviefeed.data.pojo.PopularMovies
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesDataSource(private val apiService : ApiService, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, PopularMovies>(){

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PopularMovies>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.popularMovies, null, page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PopularMovies>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key) {
                            callback.onResult(it.popularMovies, params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PopularMovies>) {
    }

}