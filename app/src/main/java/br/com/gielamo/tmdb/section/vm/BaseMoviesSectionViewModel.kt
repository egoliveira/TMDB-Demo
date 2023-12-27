package br.com.gielamo.tmdb.section.vm

import androidx.lifecycle.viewModelScope
import br.com.gielamo.tmdb.base.vm.BaseViewModel
import br.com.gielamo.tmdb.domain.exception.NotFoundException
import br.com.gielamo.tmdb.domain.exception.RequestException
import br.com.gielamo.tmdb.domain.vo.Movie
import br.com.gielamo.tmdb.domain.vo.MovieList
import br.com.gielamo.tmdb.main.view.Navigator
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import java.util.TreeMap

abstract class BaseMoviesSectionViewModel(private val navigator: Navigator) :
    BaseViewModel<BaseMoviesSectionViewModelState, BaseMoviesSectionViewModelMessage>() {
    private val pages = TreeMap<Int, MovieList>()

    private var lastLoadedPage = 0

    private var lastPage = 0

    fun loadInitialMovies() {
        postNewState(viewState = BaseMoviesSectionViewModelViewState.LOADING)

        loadPage()
    }

    fun loadMoreMovies() {
        if ((lastLoadedPage < lastPage) && !isLoading()) {
            postNewState(viewState = BaseMoviesSectionViewModelViewState.LOADING_MORE)

            loadPage()
        } else {
            Timber.w("Can't load more movies. Last page has been reached.")
        }
    }

    fun openMovieDetails(movie: Movie) {
        navigator.openMovieDetails(movie)
    }

    protected abstract suspend fun loadMovies(page: Int = 1): MovieList

    override fun getDefaultState(): BaseMoviesSectionViewModelState {
        return BaseMoviesSectionViewModelState(
            movies = emptyList(),
            viewState = BaseMoviesSectionViewModelViewState.LOADING
        )
    }

    override fun onInitialize() {
        loadInitialMovies()
    }

    protected fun getLanguageStr(): String {
        val locale = Locale.getDefault()

        return "${locale.language.lowercase()}-${locale.country.lowercase()}"
    }

    private fun loadPage() {
        viewModelScope.launch {
            try {
                val page = if (lastLoadedPage < 1) 1 else lastLoadedPage + 1
                val list = loadMovies(page = page)

                pages[list.page] = list
                lastLoadedPage = maxOf(lastLoadedPage, list.page)
                lastPage = list.totalPages

                postNewState(
                    movies = getMoviesList(),
                    viewState = if (pages.isEmpty()) {
                        BaseMoviesSectionViewModelViewState.EMPTY
                    } else {
                        BaseMoviesSectionViewModelViewState.NORMAL
                    }
                )
            } catch (ex: NotFoundException) {
                handlePageLoadingException()
            } catch (ex: RequestException) {
                handlePageLoadingException()
            }
        }
    }

    private fun handlePageLoadingException() {
        if (pages.isEmpty()) {
            postNewState(viewState = BaseMoviesSectionViewModelViewState.ERROR)
        } else {
            postNewState(viewState = BaseMoviesSectionViewModelViewState.NORMAL)
            postMessage(BaseMoviesSectionViewModelMessage.CouldNotLoadMoreMoviesError)
        }
    }

    private fun postNewState(
        movies: List<Movie> = state.value.movies,
        viewState: BaseMoviesSectionViewModelViewState = state.value.viewState
    ) {
        postState(
            BaseMoviesSectionViewModelState(
                movies = movies,
                viewState = viewState
            )
        )
    }

    private fun getMoviesList(): List<Movie> {
        return pages.flatMap { it.value.results }
    }

    private fun isLoading(): Boolean {
        return state.value.viewState in listOf(
            BaseMoviesSectionViewModelViewState.LOADING,
            BaseMoviesSectionViewModelViewState.LOADING_MORE
        )
    }
}