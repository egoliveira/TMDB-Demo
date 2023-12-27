package br.com.gielamo.tmdb.data.vo

data class RemoteMovieCredits(
    val id: Int,
    val cast: List<RemoteCastMember>,
    val crew: List<RemoteCrewMember>
)