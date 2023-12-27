package br.com.gielamo.tmdb.domain.usecase

interface UseCase<P, R> {
    suspend fun execute(params: P): R
}