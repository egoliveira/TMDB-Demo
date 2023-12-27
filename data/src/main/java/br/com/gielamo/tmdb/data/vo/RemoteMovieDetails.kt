package br.com.gielamo.tmdb.data.vo

import java.time.LocalDate

data class RemoteMovieDetails(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: RemoteMovieCollection?,
    val budget: Long?,
    val genres: List<RemoteGenre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val production_companies: List<RemoteProductionCompany>,
    val production_countries: List<RemoteCountry>,
    val release_date: LocalDate?,
    val revenue: Long?,
    val runtime: Int,
    val spoken_languages: List<RemoteLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
)