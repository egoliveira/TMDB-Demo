package br.com.gielamo.tmdb.data.mapper

import br.com.gielamo.tmdb.data.vo.RemoteMovieListResult
import br.com.gielamo.tmdb.domain.vo.MovieList
import javax.inject.Inject

class MovieListMapper @Inject constructor(private val movieMapper: MovieMapper) {
    fun fromRemoteMovieListResult(remoteMovieListResult: RemoteMovieListResult): MovieList {
        return MovieList(
            page = remoteMovieListResult.page,
            totalPages = remoteMovieListResult.total_pages,
            totalResults = remoteMovieListResult.total_results,
            results = remoteMovieListResult.results.map { movieMapper.fromRemoteMovie(it) }
        )
    }
}