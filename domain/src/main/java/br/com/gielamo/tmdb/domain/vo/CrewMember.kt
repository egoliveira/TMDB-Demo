package br.com.gielamo.tmdb.domain.vo

data class CrewMember(
    val adult: Boolean,
    val creditId: String?,
    val department: String?,
    val gender: Int,
    val id: Int,
    val job: String?,
    val knownForDepartment: String?,
    val name: String,
    val originalName: String,
    val popularity: Float,
    val profileUrl: String?
)