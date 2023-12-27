package br.com.gielamo.tmdb.section.vm

import br.com.gielamo.tmdb.domain.usecase.movie.RetrievePopularMoviesUseCase
import br.com.gielamo.tmdb.domain.vo.MovieList
import br.com.gielamo.tmdb.main.view.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesSectionViewModel @Inject constructor(
    private val retrievePopularMoviesUseCase: RetrievePopularMoviesUseCase,
    navigator: Navigator
) : BaseMoviesSectionViewModel(navigator) {
    override suspend fun loadMovies(page: Int): MovieList {
        return retrievePopularMoviesUseCase.execute(
            RetrievePopularMoviesUseCase.Params(
                page = page,
                language = getLanguageStr()
            )
        )
    }
}