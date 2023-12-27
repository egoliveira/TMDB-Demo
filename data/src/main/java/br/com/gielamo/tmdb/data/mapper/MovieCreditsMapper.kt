package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteMovieCredits
import br.com.gielamo.tmdb.domain.vo.MovieCredits
import javax.inject.Inject

class MovieCreditsMapper @Inject constructor(
    private val castMemberMapper: CastMemberMapper,
    private val crewMemberMapper: CrewMemberMapper
) {
    fun fromRemoteMovieCredits(remoteMovieCredits: RemoteMovieCredits): MovieCredits {
        return MovieCredits(
            id = remoteMovieCredits.id,
            cast = castMemberMapper.fromRemoteCastMemberList(remoteMovieCredits.cast),
            crew = crewMemberMapper.fromRemoteCrewMemberList(remoteMovieCredits.crew)
        )
    }
}