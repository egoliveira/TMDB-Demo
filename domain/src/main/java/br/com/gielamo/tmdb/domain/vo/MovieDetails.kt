package br.com.gielamo.tmdb.domain.vo

import java.time.LocalDate

data class MovieDetails(
    val adult: Boolean,
    val backdropUrl: String?,
    val belongsToCollection: MovieCollection?,
    val budget: Long?,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Float,
    val posterUrl: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<Country>,
    val releaseDate: LocalDate?,
    val revenue: Long?,
    val runtime: Int,
    val spokenLanguages: List<Language>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Float,
    val voteCount: Int
)