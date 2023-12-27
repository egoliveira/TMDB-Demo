package br.com.gielamo.tmdb.section.vm

import br.com.gielamo.tmdb.domain.usecase.movie.RetrieveUpcomingMoviesUseCase
import br.com.gielamo.tmdb.domain.vo.MovieList
import br.com.gielamo.tmdb.main.view.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesSectionViewModel @Inject constructor(
    private val retrieveUpcomingMoviesUseCase: RetrieveUpcomingMoviesUseCase,
    navigator: Navigator
) : BaseMoviesSectionViewModel(navigator) {
    override suspend fun loadMovies(page: Int): MovieList {
        return retrieveUpcomingMoviesUseCase.execute(
            RetrieveUpcomingMoviesUseCase.Params(
                page = page,
                language = getLanguageStr()
            )
        )
    }
}