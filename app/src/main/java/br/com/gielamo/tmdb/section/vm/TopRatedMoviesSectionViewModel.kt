package br.com.gielamo.tmdb.section.vm

import br.com.gielamo.tmdb.domain.usecase.movie.RetrieveTopRatedMoviesUseCase
import br.com.gielamo.tmdb.domain.vo.MovieList
import br.com.gielamo.tmdb.main.view.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesSectionViewModel @Inject constructor(
    private val retrieveTopRatedMoviesUseCase: RetrieveTopRatedMoviesUseCase,
    navigator: Navigator
) : BaseMoviesSectionViewModel(navigator) {
    override suspend fun loadMovies(page: Int): MovieList {
        return retrieveTopRatedMoviesUseCase.execute(
            RetrieveTopRatedMoviesUseCase.Params(
                page = page,
                language = getLanguageStr()
            )
        )
    }
}