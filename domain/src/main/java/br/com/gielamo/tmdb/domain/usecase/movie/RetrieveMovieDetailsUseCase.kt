package br.com.gielamo.tmdb.domain.usecase.movie

import br.com.gielamo.tmdb.domain.repository.MovieRepository
import br.com.gielamo.tmdb.domain.usecase.UseCase
import br.com.gielamo.tmdb.domain.vo.Movie
import br.com.gielamo.tmdb.domain.vo.MovieDetails
import javax.inject.Inject

class RetrieveMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<RetrieveMovieDetailsUseCase.Params, MovieDetails> {
    override suspend fun execute(params: Params): MovieDetails {
        return movieRepository.getMovieDetails(params.movie.id, params.language)
    }

    data class Params(val movie: Movie, val language: String? = null)
}