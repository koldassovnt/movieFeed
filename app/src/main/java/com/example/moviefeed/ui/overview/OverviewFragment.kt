package com.example.moviefeed.ui.overview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefeed.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.overview_fragment.*
import timber.log.Timber

@AndroidEntryPoint
class OverviewFragment : Fragment(R.layout.overview_fragment) {

    private lateinit var movieAdapter: MovieAdapter
    private val overviewViewModel by viewModels<OverviewViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        overviewViewModel.trendingMovies.observe(viewLifecycleOwner, Observer {
            movieAdapter.setMovies(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter()

        rv_movies.layoutManager = LinearLayoutManager(requireContext())
        rv_movies.adapter = movieAdapter
    }
}