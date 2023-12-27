package br.com.gielamo.tmdb.detail.vm

import androidx.lifecycle.viewModelScope
import br.com.gielamo.tmdb.base.vm.BaseViewModel
import br.com.gielamo.tmdb.detail.vo.UICastMember
import br.com.gielamo.tmdb.domain.exception.NotFoundException
import br.com.gielamo.tmdb.domain.exception.RequestException
import br.com.gielamo.tmdb.domain.usecase.movie.RetrieveMovieCreditsUseCase
import br.com.gielamo.tmdb.domain.usecase.movie.RetrieveMovieDetailsUseCase
import br.com.gielamo.tmdb.domain.vo.Movie
import br.com.gielamo.tmdb.domain.vo.MovieCredits
import br.com.gielamo.tmdb.domain.vo.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val retrieveMovieDetailsUseCase: RetrieveMovieDetailsUseCase,
    private val retrieveMovieCreditsUseCase: RetrieveMovieCreditsUseCase
) : BaseViewModel<MovieDetailsViewModelState, Unit>() {
    lateinit var movie: Movie

    fun reloadMovieDetails() {
        onInitialize()
    }

    override fun onInitialize() {
        postNewState(
            movieBackdropUrl = movie.backdropUrl,
            movieTitle = movie.title,
            viewState = MovieDetailsViewModelViewState.LOADING
        )

        viewModelScope.launch {
            val details = loadMovieDetails()

            if (details != null) {
                val credits = loadMovieCredits()

                if (credits != null) {
                    val castMembers = credits.cast.sortedBy { it.order }.map {
                        UICastMember(it.name, it.character, it.profileUrl)
                    }

                    postNewState(
                        movieDetails = details,
                        castMembers = castMembers,
                        viewState = MovieDetailsViewModelViewState.NORMAL
                    )
                } else {
                    postNewState(viewState = MovieDetailsViewModelViewState.ERROR)
                }
            } else {
                postNewState(viewState = MovieDetailsViewModelViewState.ERROR)
            }
        }
    }

    override fun getDefaultState(): MovieDetailsViewModelState {
        val hasMovie = this::movie.isInitialized

        return MovieDetailsViewModelState(
            movieBackdropUrl = if (hasMovie) movie.backdropUrl else null,
            movieTitle = if (hasMovie) movie.title else "",
            movieDetails = MovieDetails(
                adult = if (hasMovie) movie.adult else false,
                backdropUrl = if (hasMovie) movie.backdropUrl else "",
                belongsToCollection = null,
                budget = null,
                genres = emptyList(),
                homepage = null,
                id = if (hasMovie) movie.id else 0,
                imdbId = null,
                originalLanguage = if (hasMovie) movie.originalLanguage else "",
                originalTitle = if (hasMovie) movie.originalTitle else "",
                overview = if (hasMovie) movie.overview else "",
                popularity = 0f,
                posterUrl = if (hasMovie) movie.posterUrl else null,
                productionCompanies = emptyList(),
                productionCountries = emptyList(),
                releaseDate = if (hasMovie) movie.releaseDate else null,
                revenue = null,
                runtime = 0,
                spokenLanguages = emptyList(),
                status = "",
                tagline = null,
                title = if (hasMovie) movie.title else "",
                video = if (hasMovie) movie.video else false,
                voteAverage = if (hasMovie) movie.voteAverage else 0f,
                voteCount = if (hasMovie) movie.voteCount else 0
            ),
            castMembers = emptyList(),
            viewState = MovieDetailsViewModelViewState.LOADING
        )
    }

    private suspend fun loadMovieDetails(): MovieDetails? {
        return try {
            retrieveMovieDetailsUseCase.execute(
                RetrieveMovieDetailsUseCase.Params(movie)
            )
        } catch (ex: NotFoundException) {
            Timber.e("Could not load movie details. Movie not found.")
            null
        } catch (ex: RequestException) {
            Timber.e("Could not load movie details. Request error.")
            null
        }
    }

    private suspend fun loadMovieCredits(): MovieCredits? {
        return try {
            retrieveMovieCreditsUseCase.execute(
                RetrieveMovieCreditsUseCase.Params(movie)
            )
        } catch (ex: NotFoundException) {
            Timber.e("Could not load movie credits. Movie not found.")
            null
        } catch (ex: RequestException) {
            Timber.e("Could not load movie credits. Request error.")
            null
        }
    }

    private fun postNewState(
        movieBackdropUrl: String? = state.value.movieBackdropUrl,
        movieTitle: String = state.value.movieTitle,
        movieDetails: MovieDetails = state.value.movieDetails,
        castMembers: List<UICastMember> = state.value.castMembers,
        viewState: MovieDetailsViewModelViewState = state.value.viewState
    ) {
        postState(
            MovieDetailsViewModelState(
                movieBackdropUrl = movieBackdropUrl,
                movieTitle = movieTitle,
                movieDetails = movieDetails,
                castMembers = castMembers,
                viewState = viewState
            )
        )
    }
}