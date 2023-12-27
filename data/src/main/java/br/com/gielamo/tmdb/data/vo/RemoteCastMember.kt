package br.com.gielamo.tmdb.data.vo

data class RemoteCastMember(
    val adult: Boolean,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String?,
    val name: String,
    val order: Int,
    val original_name: String?,
    val popularity: Float,
    val profile_path: String?
)