package br.com.gielamo.tmdb.detail.vm

import br.com.gielamo.tmdb.detail.vo.UICastMember
import br.com.gielamo.tmdb.domain.vo.MovieDetails

data class MovieDetailsViewModelState(
    val movieBackdropUrl: String?,
    val movieTitle: String,
    val movieDetails: MovieDetails,
    val castMembers: List<UICastMember>,
    val viewState: MovieDetailsViewModelViewState
)
