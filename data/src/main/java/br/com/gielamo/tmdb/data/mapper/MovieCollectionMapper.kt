package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteMovieCollection
import br.com.gielamo.tmdb.domain.vo.MovieCollection
import javax.inject.Inject

class MovieCollectionMapper @Inject constructor(private val imageMapper: ImageMapper) {
    fun fromRemoteMovieCollection(remoteMovieCollection: RemoteMovieCollection?): MovieCollection? {
        return if (remoteMovieCollection != null) {
            MovieCollection(
                id = remoteMovieCollection.id,
                name = remoteMovieCollection.name,
                posterUrl = imageMapper.fromImagePath(remoteMovieCollection.poster_path),
                backdropUrl = imageMapper.fromImagePath(remoteMovieCollection.backdrop_path)
            )
        } else null
    }
}