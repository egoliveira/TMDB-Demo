package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteMovie
import br.com.gielamo.tmdb.domain.vo.Movie
import javax.inject.Inject

class MovieMapper @Inject constructor(private val imageMapper: ImageMapper) {
    fun fromRemoteMovie(remoteMovie: RemoteMovie): Movie {
        return Movie(
            adult = remoteMovie.adult,
            backdropUrl = imageMapper.fromImagePath(remoteMovie.backdrop_path),
            genreIds = remoteMovie.genre_ids,
            id = remoteMovie.id,
            originalLanguage = remoteMovie.original_language,
            originalTitle = remoteMovie.original_title,
            overview = remoteMovie.overview,
            popularity = remoteMovie.popularity,
            posterUrl = imageMapper.fromImagePath(remoteMovie.poster_path),
            releaseDate = remoteMovie.release_date,
            title = remoteMovie.title,
            video = remoteMovie.video,
            voteAverage = remoteMovie.vote_average,
            voteCount = remoteMovie.vote_count
        )
    }
}