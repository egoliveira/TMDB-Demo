package br.com.gielamo.tmdb.domain.vo

data class CastMember(
    val adult: Boolean,
    val castId: Int,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String?,
    val name: String,
    val order: Int,
    val originalName: String?,
    val popularity: Float,
    val profileUrl: String?
)