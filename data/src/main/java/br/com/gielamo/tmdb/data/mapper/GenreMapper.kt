package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteGenre
import br.com.gielamo.tmdb.domain.vo.Genre
import javax.inject.Inject

class GenreMapper @Inject constructor() {
    fun fromRemoteGenre(remoteGenre: RemoteGenre): Genre {
        return Genre(
            id = remoteGenre.id,
            name = remoteGenre.name
        )
    }

    fun fromRemoteGenreList(remoteGenreList: List<RemoteGenre>): List<Genre> {
        return remoteGenreList.map {
            fromRemoteGenre(it)
        }
    }
}