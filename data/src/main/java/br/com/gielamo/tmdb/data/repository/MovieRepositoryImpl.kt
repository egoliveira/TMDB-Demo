package br.com.gielamo.tmdb.data.repository

import br.com.gielamo.tmdb.data.mapper.MovieCreditsMapper
import br.com.gielamo.tmdb.data.mapper.MovieDetailsMapper
import br.com.gielamo.tmdb.data.mapper.MovieListMapper
import br.com.gielamo.tmdb.data.mechanism.service.MoviesService
import br.com.gielamo.tmdb.domain.exception.NotFoundException
import br.com.gielamo.tmdb.domain.exception.RequestException
import br.com.gielamo.tmdb.domain.repository.MovieRepository
import br.com.gielamo.tmdb.domain.vo.MovieCredits
import br.com.gielamo.tmdb.domain.vo.MovieDetails
import br.com.gielamo.tmdb.domain.vo.MovieList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.util.Locale

class MovieRepositoryImpl(
    private val moviesService: MoviesService,
    private val movieListMapper: MovieListMapper,
    private val movieDetailsMapper: MovieDetailsMapper,
    private val movieCreditsMapper: MovieCreditsMapper,
    private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {
    override suspend fun getNowPlayingMovies(page: Int, language: String?): MovieList {
        val lang = fixLanguage(language)

        Timber.d("Retrieving now playing movies. Page: %d, language: %s", page, lang)

        return withContext(ioDispatcher) {
            try {
                val call = moviesService.getNowPlayingMovies(page, lang)
                val response = call.execute()

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body == null) {
                        Timber.e("Empty response while retrieving now playing movies")

                        throw NotFoundException("Empty response while retrieving now playing movies")
                    } else {
                        val movies = movieListMapper.fromRemoteMovieListResult(body)

                        Timber.d(
                            "Number of now playing movies retrieved from page %d: %d. Total " +
                                    "pages: %d, total records: %d", page,
                            movies.results.size, movies.totalPages, movies.totalResults
                        )

                        movies
                    }
                } else {
                    Timber.e(
                        "Could not retrieve now playing movies. Response code: %d",
                        response.code()
                    )

                    throw RequestException(
                        "Could not retrieve now playing movies. Response code: ${response.code()}"
                    )
                }
            } catch (ex: IOException) {
                Timber.e(ex, "An error has occurred while retrieving data from now playing movies")

                throw RequestException(
                    "An error has occurred while retrieving data from now playing movies",
                    ex
                )
            } catch (ex: RuntimeException) {
                Timber.e(ex, "An error has occurred during the request of now playing movies")

                throw RequestException(
                    "An error has occurred during the request of now playing movies",
                    ex
                )
            }
        }
    }

    override suspend fun getPopularMovies(page: Int, language: String?): MovieList {
        val lang = fixLanguage(language)

        Timber.d("Retrieving popular movies. Page: %d, language: %s", page, lang)

        return withContext(ioDispatcher) {
            try {
                val call = moviesService.getPopularMovies(page, lang)
                val response = call.execute()

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body == null) {
                        Timber.e("Empty response while retrieving popular movies")

                        throw NotFoundException("Empty response while retrieving popular movies")
                    } else {
                        val movies = movieListMapper.fromRemoteMovieListResult(body)

                        Timber.d(
                            "Number of popular movies retrieved from page %d: %d. Total " +
                                    "pages: %d, total records: %d", page,
                            movies.results.size, movies.totalPages, movies.totalResults
                        )

                        movies
                    }
                } else {
                    Timber.e(
                        "Could not retrieve popular movies. Response code: %d",
                        response.code()
                    )

                    throw RequestException(
                        "Could not retrieve popular movies. Response code: ${response.code()}"
                    )
                }
            } catch (ex: IOException) {
                Timber.e(ex, "An error has occurred while retrieving data from popular movies")

                throw RequestException(
                    "An error has occurred while retrieving data from popular movies",
                    ex
                )
            } catch (ex: RuntimeException) {
                Timber.e(ex, "An error has occurred during the request of popular movies")

                throw RequestException(
                    "An error has occurred during the request of popular movies",
                    ex
                )
            }
        }
    }

    override suspend fun getTopRatedMovies(page: Int, language: String?): MovieList {
        val lang = fixLanguage(language)

        Timber.d("Retrieving top rated movies. Page: %d, language: %s", page, lang)

        return withContext(ioDispatcher) {
            try {
                val call = moviesService.getTopRatedMovies(page, lang)
                val response = call.execute()

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body == null) {
                        Timber.e("Empty response while retrieving top rated movies")

                        throw NotFoundException("Empty response while retrieving top rated movies")
                    } else {
                        val movies = movieListMapper.fromRemoteMovieListResult(body)

                        Timber.d(
                            "Number of top rated movies retrieved from page %d: %d. Total " +
                                    "pages: %d, total records: %d", page,
                            movies.results.size, movies.totalPages, movies.totalResults
                        )

                        movies
                    }
                } else {
                    Timber.e(
                        "Could not retrieve top rated movies. Response code: %d",
                        response.code()
                    )

                    throw RequestException(
                        "Could not retrieve top rated movies. Response code: ${response.code()}"
                    )
                }
            } catch (ex: IOException) {
                Timber.e(ex, "An error has occurred while retrieving data from top rated movies")

                throw RequestException(
                    "An error has occurred while retrieving data from top rated movies",
                    ex
                )
            } catch (ex: RuntimeException) {
                Timber.e(ex, "An error has occurred during the request of top rated movies")

                throw RequestException(
                    "An error has occurred during the request of top rated movies",
                    ex
                )
            }
        }
    }

    override suspend fun getUpcomingMovies(page: Int, language: String?): MovieList {
        val lang = fixLanguage(language)

        Timber.d("Retrieving upcoming movies. Page: %d, language: %s", page, lang)

        return withContext(ioDispatcher) {
            try {
                val call = moviesService.getUpcomingMovies(page, lang)
                val response = call.execute()

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body == null) {
                        Timber.e("Empty response while retrieving upcoming movies")

                        throw NotFoundException("Empty response while retrieving upcoming movies")
                    } else {
                        val movies = movieListMapper.fromRemoteMovieListResult(body)

                        Timber.d(
                            "Number of upcoming movies retrieved from page %d: %d. Total " +
                                    "pages: %d, total records: %d", page,
                            movies.results.size, movies.totalPages, movies.totalResults
                        )

                        movies
                    }
                } else {
                    Timber.e(
                        "Could not retrieve upcoming movies. Response code: %d",
                        response.code()
                    )

                    throw RequestException(
                        "Could not retrieve upcoming movies. Response code: ${response.code()}"
                    )
                }
            } catch (ex: IOException) {
                Timber.e(ex, "An error has occurred while retrieving data from upcoming movies")

                throw RequestException(
                    "An error has occurred while retrieving data from upcoming movies",
                    ex
                )
            } catch (ex: RuntimeException) {
                Timber.e(ex, "An error has occurred during the request of upcoming movies")

                throw RequestException(
                    "An error has occurred during the request of upcoming movies",
                    ex
                )
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int, language: String?): MovieDetails {
        val lang = fixLanguage(language)

        Timber.d("Retrieving details of movie id $movieId, language: %s", lang)

        return withContext(ioDispatcher) {
            try {
                val call = moviesService.getMovieDetails(movieId, lang)
                val response = call.execute()

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body == null) {
                        Timber.e("Empty response while retrieving movie details")

                        throw NotFoundException("Empty response while retrieving movie details")
                    } else {
                        val movieDetails = movieDetailsMapper.fromRemoteMovieDetail(body)

                        Timber.d("Movie details of movie id %d retrieved", movieId)

                        movieDetails
                    }
                } else {
                    Timber.e(
                        "Could not retrieve movie details. Response code: %d",
                        response.code()
                    )

                    throw RequestException(
                        "Could not retrieve movie details. Response code: ${response.code()}"
                    )
                }
            } catch (ex: IOException) {
                Timber.e(ex, "An error has occurred while retrieving data from movie details")

                throw RequestException(
                    "An error has occurred while retrieving data from movie details",
                    ex
                )
            } catch (ex: RuntimeException) {
                Timber.e(ex, "An error has occurred during the request of movie details")

                throw RequestException(
                    "An error has occurred during the request of movie details",
                    ex
                )
            }
        }
    }

    override suspend fun getMovieCredits(movieId: Int, language: String?): MovieCredits {
        val lang = fixLanguage(language)

        Timber.d("Retrieving credits of movie id $movieId, language: %s", lang)

        return withContext(ioDispatcher) {
            try {
                val call = moviesService.getMovieCredits(movieId, lang)
                val response = call.execute()

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body == null) {
                        Timber.e("Empty response while retrieving movie credits")

                        throw NotFoundException("Empty response while retrieving movie credits")
                    } else {
                        val movieCredits = movieCreditsMapper.fromRemoteMovieCredits(body)

                        Timber.d("Credits of movie id %d retrieved", movieId)

                        movieCredits
                    }
                } else {
                    Timber.e(
                        "Could not retrieve movie credits. Response code: %d",
                        response.code()
                    )

                    throw RequestException(
                        "Could not retrieve movie credits. Response code: ${response.code()}"
                    )
                }
            } catch (ex: IOException) {
                Timber.e(ex, "An error has occurred while retrieving data from movie credits")

                throw RequestException(
                    "An error has occurred while retrieving data from movie credits",
                    ex
                )
            } catch (ex: RuntimeException) {
                Timber.e(ex, "An error has occurred during the request of movie credits")

                throw RequestException(
                    "An error has occurred during the request of movie credits",
                    ex
                )
            }
        }
    }

    private fun fixLanguage(language: String?): String {
        return if (language.isNullOrBlank()) {
            val locale = Locale.getDefault()

            "${locale.language.lowercase()}-${locale.country.lowercase()}"
        } else language
    }
}