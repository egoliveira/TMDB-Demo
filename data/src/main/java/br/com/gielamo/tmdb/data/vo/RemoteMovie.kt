package br.com.gielamo.tmdb.data.vo

import java.time.LocalDate

data class RemoteMovie(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val release_date: LocalDate?,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
)
