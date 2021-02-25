package com.example.moviefeed.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefeed.R
import com.example.moviefeed.glide.GlideApp
import com.example.moviefeed.model.Movie
import kotlinx.android.synthetic.main.view_item.view.*

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            itemView.apply {
                GlideApp.with(iv_poster)
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .into(iv_poster)
                tv_title.text = movie.title
                tv_overview.text = movie.overview
                tv_releaseDate.text = movie.releaseDate
            }
        }

        companion object {
            fun from(parent: ViewGroup): MovieViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.view_item, parent, false)
                return MovieViewHolder(itemView)
            }
        }
    }
}