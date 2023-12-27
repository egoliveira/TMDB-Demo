package br.com.gielamo.tmdb.main.view

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import br.com.gielamo.tmdb.domain.vo.Movie
import br.com.gielamo.tmdb.main.view.Destinations.MovieDetails
import com.squareup.moshi.Moshi
import java.net.URLDecoder
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

sealed class Destinations(open val name: String) {
    object Main : Destinations("main")

    object MovieDetails : Destinations("movieDetails") {
        const val MOVIE_PARAMETER = "movieJson"

        fun getMovieParameter(navBackStackEntry: NavBackStackEntry, moshi: Moshi): Movie? {
            val movieJson = navBackStackEntry.arguments?.getString(MOVIE_PARAMETER)

            return if (!movieJson.isNullOrBlank()) {
                val decodedMovieJson = Navigator.decodeParameter(movieJson)

                moshi.adapter(Movie::class.java).fromJson(decodedMovieJson)
            } else null
        }
    }
}

@Singleton
class Navigator @Inject constructor(private val moshi: Moshi) {
    lateinit var navController: NavHostController

    fun openMovieDetails(movie: Movie) {
        val movieJson = moshi.adapter(Movie::class.java).toJson(movie)
        val movieData = encodeParameter(movieJson)

        navController.navigate("${MovieDetails.name}/$movieData")
    }

    companion object {
        private const val UTF8 = "UTF-8"

        fun encodeParameter(param: String): String {
            return URLEncoder.encode(param, UTF8)
        }

        fun decodeParameter(param: String): String {
            return URLDecoder.decode(param, UTF8)
        }
    }
}