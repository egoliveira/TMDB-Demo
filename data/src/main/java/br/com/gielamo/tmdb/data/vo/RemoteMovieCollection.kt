package br.com.gielamo.tmdb.data.vo

data class RemoteMovieCollection(
    val id: Int,
    val name: String,
    val poster_path: String?,
    val backdrop_path: String?
)