package br.com.gielamo.tmdb.main.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.gielamo.tmdb.detail.view.MovieDetailsScreen
import br.com.gielamo.tmdb.ui.theme.TMDBDemoTheme
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var moshi: Moshi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            navigator.navController = navController

            TMDBDemoTheme {
                NavHost(navController = navController, startDestination = Destinations.Main.name) {
                    composable(Destinations.Main.name) {
                        MainScreen()
                    }

                    composable(
                        "${Destinations.MovieDetails.name}/{${Destinations.MovieDetails.MOVIE_PARAMETER}}",
                        arguments = listOf(
                            navArgument(Destinations.MovieDetails.MOVIE_PARAMETER) {
                                type = NavType.StringType
                            }
                        )
                    ) { navBackStackEntry ->
                        val movie =
                            Destinations.MovieDetails.getMovieParameter(navBackStackEntry, moshi)

                        if (movie != null) {
                            MovieDetailsScreen(movie = movie)
                        }
                    }
                }
            }
        }
    }
}