package br.com.gielamo.tmdb.domain.usecase.movie

import br.com.gielamo.tmdb.domain.repository.MovieRepository
import br.com.gielamo.tmdb.domain.usecase.UseCase
import br.com.gielamo.tmdb.domain.vo.MovieList
import javax.inject.Inject

class RetrieveTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<RetrieveTopRatedMoviesUseCase.Params, MovieList> {
    override suspend fun execute(params: Params): MovieList {
        return movieRepository.getTopRatedMovies(params.page, params.language)
    }

    data class Params(val page: Int = 1, val language: String? = null)
}