package br.com.gielamo.tmdb.domain.vo

data class ProductionCompany(
    val id: Int,
    val logoUrl: String?,
    val name: String,
    val originCountry: String
)
