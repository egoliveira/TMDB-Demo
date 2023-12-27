package br.com.gielamo.tmdb.main.vm

import br.com.gielamo.tmdb.main.vo.MovieSection

data class MainViewModelState(
    val sections: List<MovieSection>,
    val selectedSection: MovieSection
)
