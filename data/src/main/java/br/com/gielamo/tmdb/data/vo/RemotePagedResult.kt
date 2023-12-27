package br.com.gielamo.tmdb.data.vo

interface RemotePagedResult<T> {
    val page: Int
    val total_pages: Int
    val total_results: Int
    val results: List<T>
}