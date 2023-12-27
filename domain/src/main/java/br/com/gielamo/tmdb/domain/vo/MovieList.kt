package br.com.gielamo.tmdb.domain.vo

data class MovieList(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val results: List<Movie>
)