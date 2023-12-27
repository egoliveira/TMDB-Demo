package br.com.gielamo.tmdb.data.vo

data class RemoteCrewMember(
    val adult: Boolean,
    val credit_id: String?,
    val department: String?,
    val gender: Int,
    val id: Int,
    val job: String?,
    val known_for_department: String?,
    val name: String,
    val original_name: String,
    val popularity: Float,
    val profile_path: String?
)