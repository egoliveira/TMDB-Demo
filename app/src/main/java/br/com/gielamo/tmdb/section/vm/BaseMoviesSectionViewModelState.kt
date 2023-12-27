package br.com.gielamo.tmdb.section.vm

import br.com.gielamo.tmdb.domain.vo.Movie

data class BaseMoviesSectionViewModelState(
    val movies: List<Movie>,
    val viewState: BaseMoviesSectionViewModelViewState
)