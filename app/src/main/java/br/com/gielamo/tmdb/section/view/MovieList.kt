package br.com.gielamo.tmdb.section.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.gielamo.tmdb.R
import br.com.gielamo.tmdb.base.extension.collectAsEffect
import br.com.gielamo.tmdb.base.view.ErrorView
import br.com.gielamo.tmdb.base.view.MovieProgressIndicator
import br.com.gielamo.tmdb.domain.vo.Movie
import br.com.gielamo.tmdb.section.vm.BaseMoviesSectionViewModel
import br.com.gielamo.tmdb.section.vm.BaseMoviesSectionViewModelMessage
import br.com.gielamo.tmdb.section.vm.BaseMoviesSectionViewModelViewState
import br.com.gielamo.tmdb.section.vm.NowPlayingMoviesSectionViewModel
import br.com.gielamo.tmdb.section.vm.PopularMoviesSectionViewModel
import br.com.gielamo.tmdb.section.vm.TopRatedMoviesSectionViewModel
import br.com.gielamo.tmdb.section.vm.UpcomingMoviesSectionViewModel
import br.com.gielamo.tmdb.ui.theme.Palette
import java.time.LocalDate

@Composable
fun MovieList(viewModel: BaseMoviesSectionViewModel) {
    LocalLifecycleOwner.current.lifecycle.addObserver(viewModel)

    val state by viewModel.state.collectAsState()
    val moviesListState = rememberLazyListState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            val lastVisibleItemIndex =
                moviesListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

            state.movies.isNotEmpty() && (state.movies.size - PAGING_THRESHOLD < lastVisibleItemIndex)
        }
    }

    val context = LocalContext.current

    viewModel.message.collectAsEffect {
        handleMessage(it, context)
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (state.viewState in listOf(
                BaseMoviesSectionViewModelViewState.NORMAL,
                BaseMoviesSectionViewModelViewState.LOADING_MORE
            )
        ) {
            LazyColumn(
                state = moviesListState,
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(state.movies) { index, movie ->
                    MovieItem(movie = movie,
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clickable { viewModel.openMovieDetails(movie) })

                    if (index != state.movies.size - 1) {
                        Divider(
                            color = Palette.Transparent,
                            thickness = 8.dp
                        )
                    }
                }

                if (state.viewState == BaseMoviesSectionViewModelViewState.LOADING_MORE) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            MovieProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            LaunchedEffect(key1 = shouldStartPaginate.value) {
                if (shouldStartPaginate.value) {
                    viewModel.loadMoreMovies()
                }
            }
        }

        if (state.viewState == BaseMoviesSectionViewModelViewState.LOADING) {
            MovieProgressIndicator()
        }

        if (state.viewState in listOf(
                BaseMoviesSectionViewModelViewState.EMPTY,
                BaseMoviesSectionViewModelViewState.ERROR
            )
        ) {
            val messageResId = when (state.viewState) {
                BaseMoviesSectionViewModelViewState.EMPTY ->
                    R.string.movie_list_empty_movies_section_message

                BaseMoviesSectionViewModelViewState.ERROR ->
                    R.string.movie_list_error_loading_movies_section_error_message

                else -> 0
            }

            ErrorView(
                modifier = Modifier.padding(horizontal = 32.dp),
                errorMessage = stringResource(messageResId),
                retryButtonTitle = stringResource(R.string.movie_list_try_again_button)
            ) {
                viewModel.loadInitialMovies()
            }
        }
    }
}

@Composable
fun NowPlayingMoviesList(viewModel: NowPlayingMoviesSectionViewModel = hiltViewModel()) {
    MovieList(viewModel = viewModel)
}

@Composable
fun PopularMoviesList(viewModel: PopularMoviesSectionViewModel = hiltViewModel()) {
    MovieList(viewModel = viewModel)
}

@Composable
fun TopRatedMoviesList(viewModel: TopRatedMoviesSectionViewModel = hiltViewModel()) {
    MovieList(viewModel = viewModel)
}

@Composable
fun UpcomingMoviesList(viewModel: UpcomingMoviesSectionViewModel = hiltViewModel()) {
    MovieList(viewModel = viewModel)
}

private fun handleMessage(message: BaseMoviesSectionViewModelMessage, context: Context) {
    when (message) {
        is BaseMoviesSectionViewModelMessage.CouldNotLoadMoreMoviesError ->
            Toast.makeText(
                context,
                context.getString(R.string.movie_list_could_not_load_more_movies_error_message),
                Toast.LENGTH_LONG
            ).show()
    }
}

@Composable
@Preview
fun MoviesListPreview() {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            MovieItem(
                movie = Movie(
                    adult = false,
                    backdropUrl = "https://image.tmdb.org/t/p/original/lVy5Zqcty2NfemqKYbVJfdg44rK.jpg",
                    genreIds = listOf(28, 80),
                    id = 24,
                    originalLanguage = "en",
                    originalTitle = "Kill Bill: Vol. 1",
                    overview = "An assassin is shot by her ruthless employer, Bill, and other members of " +
                            "their assassination circle – but she lives to plot her vengeance.",
                    popularity = 43.577f,
                    posterUrl = "https://image.tmdb.org/t/p/original/v7TaX8kXMXs5yFFGR41guUDNcnB.jpg",
                    releaseDate = LocalDate.parse("2003-10-10"),
                    title = "Kill Bill: Vol. 1",
                    video = false,
                    voteAverage = 7.969f,
                    voteCount = 16233
                )
            )
        }
    }
}

private const val PAGING_THRESHOLD = 3