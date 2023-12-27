package br.com.gielamo.tmdb.domain.usecase.movie

import br.com.gielamo.tmdb.domain.repository.MovieRepository
import br.com.gielamo.tmdb.domain.usecase.UseCase
import br.com.gielamo.tmdb.domain.vo.Movie
import br.com.gielamo.tmdb.domain.vo.MovieCredits
import javax.inject.Inject

class RetrieveMovieCreditsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<RetrieveMovieCreditsUseCase.Params, MovieCredits> {
    override suspend fun execute(params: Params): MovieCredits {
        return movieRepository.getMovieCredits(params.movie.id, params.language)
    }

    data class Params(val movie: Movie, val language: String? = null)
}