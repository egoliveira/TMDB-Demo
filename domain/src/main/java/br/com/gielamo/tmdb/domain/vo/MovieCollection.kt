package br.com.gielamo.tmdb.domain.vo

data class MovieCollection(
    val id: Int,
    val name: String,
    val posterUrl: String?,
    val backdropUrl: String?
)