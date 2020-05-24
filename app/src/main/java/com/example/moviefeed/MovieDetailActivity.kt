package com.example.moviefeed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviefeed.adapter.MovieAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity() : AppCompatActivity() {

    private lateinit var viewModel : MovieViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        if (!intent.hasExtra(EXTRA_TITLE)){
            finish()
            return
        }

        val from= intent.getStringExtra(EXTRA_TITLE)

        viewModel = ViewModelProviders.of(this)[MovieViewModel::class.java]
        viewModel.getDetailInfo(from).observe(this, Observer {
            Picasso.get().load(it.getFullPosterUrl()).into(iv_movie_poster)
            movie_title.text = it.originalTitle
            movie_release_date.text = it.releaseDate
            movie_rating.text = it.voteAverage.toString()
            movie_overview.text = it.overview
        })

    }

    companion object {
        private const val EXTRA_TITLE = "movieName"

        fun newIntent(context: Context, mName: String) : Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_TITLE, mName)
            return intent
        }
    }


}