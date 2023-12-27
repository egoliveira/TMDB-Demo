package br.com.gielamo.tmdb.domain.repository

import br.com.gielamo.tmdb.domain.exception.NotFoundException
import br.com.gielamo.tmdb.domain.exception.RequestException
import br.com.gielamo.tmdb.domain.vo.MovieCredits
import br.com.gielamo.tmdb.domain.vo.MovieDetails
import br.com.gielamo.tmdb.domain.vo.MovieList

interface MovieRepository {
    @Throws(RequestException::class, NotFoundException::class)
    suspend fun getNowPlayingMovies(page: Int = 1, language: String? = null): MovieList

    @Throws(RequestException::class, NotFoundException::class)
    suspend fun getPopularMovies(page: Int = 1, language: String? = null): MovieList

    @Throws(RequestException::class, NotFoundException::class)
    suspend fun getTopRatedMovies(page: Int = 1, language: String? = null): MovieList

    @Throws(RequestException::class, NotFoundException::class)
    suspend fun getUpcomingMovies(page: Int = 1, language: String? = null): MovieList

    @Throws(RequestException::class, NotFoundException::class)
    suspend fun getMovieDetails(movieId: Int, language: String? = null): MovieDetails

    @Throws(RequestException::class, NotFoundException::class)
    suspend fun getMovieCredits(movieId: Int, language: String? = null): MovieCredits
}