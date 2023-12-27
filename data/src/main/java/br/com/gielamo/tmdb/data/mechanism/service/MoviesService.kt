package br.com.gielamo.tmdb.data.mechanism.service

import br.com.gielamo.tmdb.data.vo.RemoteMovieCredits
import br.com.gielamo.tmdb.data.vo.RemoteMovieDetails
import br.com.gielamo.tmdb.data.vo.RemoteMovieListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("page") page: Int, @Query("language") language: String):
            Call<RemoteMovieListResult>

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int, @Query("language") language: String):
            Call<RemoteMovieListResult>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int, @Query("language") language: String):
            Call<RemoteMovieListResult>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("page") page: Int, @Query("language") language: String):
            Call<RemoteMovieListResult>

    @GET("movie/{movieId}")
    fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("language") language: String
    ): Call<RemoteMovieDetails>

    @GET("movie/{movieId}/credits")
    fun getMovieCredits(
        @Path("movieId") movieId: Int,
        @Query("language") language: String
    ): Call<RemoteMovieCredits>
}