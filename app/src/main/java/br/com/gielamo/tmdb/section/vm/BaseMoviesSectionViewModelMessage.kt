package br.com.gielamo.tmdb.section.vm

sealed class BaseMoviesSectionViewModelMessage {
    object CouldNotLoadMoreMoviesError : BaseMoviesSectionViewModelMessage()
}