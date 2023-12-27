package br.com.gielamo.tmdb.data.vo

data class RemoteMovieListResult(
    override val page: Int,
    override val total_pages: Int,
    override val total_results: Int,
    override val results: List<RemoteMovie>
) : RemotePagedResult<RemoteMovie>