package br.com.gielamo.tmdb.data.di

import br.com.gielamo.tmdb.data.mapper.MovieCreditsMapper
import br.com.gielamo.tmdb.data.mapper.MovieDetailsMapper
import br.com.gielamo.tmdb.data.mapper.MovieListMapper
import br.com.gielamo.tmdb.data.mechanism.interceptor.TMDBApiInterceptor
import br.com.gielamo.tmdb.data.mechanism.json.LocalDateTypeAdapter
import br.com.gielamo.tmdb.data.mechanism.service.MoviesService
import br.com.gielamo.tmdb.data.repository.MovieRepositoryImpl
import br.com.gielamo.tmdb.domain.di.IODispatcherQualifier
import br.com.gielamo.tmdb.domain.repository.MovieRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideMovieRepository(
        moviesService: MoviesService,
        movieListMapper: MovieListMapper,
        movieDetailsMapper: MovieDetailsMapper,
        movieCreditsMapper: MovieCreditsMapper,
        @IODispatcherQualifier ioDispatcher: CoroutineDispatcher
    ): MovieRepository {
        return MovieRepositoryImpl(
            moviesService,
            movieListMapper,
            movieDetailsMapper,
            movieCreditsMapper,
            ioDispatcher
        )
    }

    @Provides
    fun provideOkHttpClient(tmdbApiInterceptor: TMDBApiInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(tmdbApiInterceptor).build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(LocalDateTypeAdapter()).build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }

    @Provides
    @IODispatcherQualifier
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}