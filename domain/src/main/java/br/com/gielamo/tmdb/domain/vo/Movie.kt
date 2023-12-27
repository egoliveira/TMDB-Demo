package br.com.gielamo.tmdb.domain.vo

import java.time.LocalDate

data class Movie(
    val adult: Boolean,
    val backdropUrl: String?,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Float,
    val posterUrl: String?,
    val releaseDate: LocalDate?,
    val title: String,
    val video: Boolean,
    val voteAverage: Float,
    val voteCount: Int
)
