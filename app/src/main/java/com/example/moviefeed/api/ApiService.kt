package com.example.moviefeed.api

import com.example.moviefeed.api.ApiFactory.API_KEY
import com.example.moviefeed.api.ApiFactory.LANGUAGE
import com.example.moviefeed.api.ApiFactory.PAGE
import com.example.moviefeed.pojo.ListOfMovies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(QUERY_PARAM_LANGUAGE) language: String = LANGUAGE,
        @Query(QUERY_PARAM_PAGE) page: Int= PAGE
    ) : Single<ListOfMovies>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LANGUAGE = "language"
        private const val QUERY_PARAM_PAGE = "page"
    }

}