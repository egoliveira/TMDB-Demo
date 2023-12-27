package br.com.gielamo.tmdb.domain.vo

data class MovieCredits(
    val id: Int,
    val cast: List<CastMember>,
    val crew: List<CrewMember>
)