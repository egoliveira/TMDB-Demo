package br.com.gielamo.tmdb.section.vm

import br.com.gielamo.tmdb.domain.usecase.movie.RetrieveNowPlayingMoviesUseCase
import br.com.gielamo.tmdb.domain.vo.MovieList
import br.com.gielamo.tmdb.main.view.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NowPlayingMoviesSectionViewModel @Inject constructor(
    private val retrieveNowPlayingMoviesUseCase: RetrieveNowPlayingMoviesUseCase,
    navigator: Navigator
) : BaseMoviesSectionViewModel(navigator) {
    override suspend fun loadMovies(page: Int): MovieList {
        return retrieveNowPlayingMoviesUseCase.execute(
            RetrieveNowPlayingMoviesUseCase.Params(
                page = page,
                language = getLanguageStr()
            )
        )
    }
}