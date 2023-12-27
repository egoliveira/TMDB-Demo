package br.com.gielamo.tmdb.di

import android.content.Context
import androidx.navigation.NavHostController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideNavHostController(@ApplicationContext context: Context): NavHostController {
        return NavHostController(context)
    }
}