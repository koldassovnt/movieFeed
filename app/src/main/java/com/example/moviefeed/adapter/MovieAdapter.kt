package com.example.moviefeed.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefeed.R
import com.example.moviefeed.pojo.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.MovieInfoViewHolder>() {

    var movieList: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieInfoViewHolder(view)    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieInfoViewHolder, position: Int) {
        var movie = movieList[position]

        with(holder) {
            with(movie) {

                tvMovieRating.text = voteAverage.toString()
                com.squareup.picasso.Picasso.get().load(getFullPosterUrl()).into(ivMoviePoster)

                itemView.setOnClickListener {
                    onMovieClickListener?.onMovieClick(this)
                }

                itemView.setOnClickListener {
                    onMovieClickListener?.onMovieClick(this)
                }
            }
        }
    }

    inner class MovieInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMoviePoster = itemView.ivMoviePoster
        val tvMovieRating = itemView.tvMovieRating
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }
}