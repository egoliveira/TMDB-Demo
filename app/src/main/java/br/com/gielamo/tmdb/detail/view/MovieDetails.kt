package br.com.gielamo.tmdb.detail.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.gielamo.tmdb.R
import br.com.gielamo.tmdb.base.view.ErrorView
import br.com.gielamo.tmdb.base.view.MovieProgressIndicator
import br.com.gielamo.tmdb.detail.vm.MovieDetailsViewModel
import br.com.gielamo.tmdb.detail.vm.MovieDetailsViewModelViewState
import br.com.gielamo.tmdb.detail.vo.UICastMember
import br.com.gielamo.tmdb.domain.vo.Movie
import br.com.gielamo.tmdb.domain.vo.MovieDetails
import br.com.gielamo.tmdb.ui.theme.Palette
import br.com.gielamo.tmdb.ui.theme.TMDBDemoTheme
import coil.compose.AsyncImage
import java.text.NumberFormat
import java.time.LocalDate

@Composable
fun MovieDetailsScreen(movie: Movie, viewModel: MovieDetailsViewModel = hiltViewModel()) {
    viewModel.movie = movie

    LocalLifecycleOwner.current.lifecycle.addObserver(viewModel)

    Scaffold {
        Box(Modifier.padding(it)) {
            MovieDetailsContent(viewModel)
            RatingBar(viewModel)
        }
    }
}

@Composable
private fun MovieDetailsContent(viewModel: MovieDetailsViewModel) {
    val state by viewModel.state.collectAsState()

    val viewState = state.viewState
    val movieDetails = state.movieDetails

    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = state.movieBackdropUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.unknown_movie_backdrop),
            error = painterResource(id = R.drawable.unknown_movie_backdrop),
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
        )

        Spacer(
            modifier = Modifier
                .animateContentSize()
                .height(
                    if (state.viewState == MovieDetailsViewModelViewState.NORMAL) 70.dp else 8.dp
                )
        )

        // Movie Title
        Text(
            text = state.movieTitle,
            style = MaterialTheme.typography.headlineLarge,
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 32.dp),
            overflow = TextOverflow.Ellipsis
        )

        when (viewState) {
            MovieDetailsViewModelViewState.NORMAL -> {
                Spacer(modifier = Modifier.height(8.dp))

                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    // Minor details
                    MinorDetails(movieDetails)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Genres
                    Genres(movieDetails)

                    Spacer(modifier = Modifier.height(48.dp))

                    // Plot Summary
                    PlotSummary(movieDetails)

                    // Cast Members
                    CastMembers(state.castMembers)

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            MovieDetailsViewModelViewState.LOADING -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MovieProgressIndicator()
                }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ErrorView(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        errorMessage = stringResource(
                            id = R.string.movie_details_error_loading_movie_details_message
                        ),
                        retryButtonTitle = stringResource(
                            id = R.string.movie_details_try_again_button_title
                        )
                    ) {
                        viewModel.reloadMovieDetails()
                    }
                }
            }
        }
    }
}

@Composable
private fun MinorDetails(movieDetails: MovieDetails) {
    val context = LocalContext.current

    Row {
        Spacer(modifier = Modifier.width(32.dp))

        // Year
        val releaseDate = movieDetails.releaseDate

        if (releaseDate != null) {
            Text(
                text = releaseDate.year.toString(),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.width(32.dp))
        }

        // Runtime
        val hours = movieDetails.runtime / MINUTES_IN_ONE_HOUR
        val minutes = movieDetails.runtime - (hours * MINUTES_IN_ONE_HOUR)

        val runtime = when {
            (hours > 0) && (minutes > 0) ->
                context.getString(
                    R.string.movie_details_movie_length_hours_minutes,
                    hours, minutes
                )

            (hours > 0) && (minutes == 0) ->
                context.getString(
                    R.string.movie_details_movie_length_hours,
                    hours
                )

            else -> context.getString(
                R.string.movie_details_movie_length_minutes,
                minutes
            )
        }

        Text(
            text = runtime,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun Genres(movieDetails: MovieDetails) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(movieDetails.genres) { index, genre ->
            if (index == 0) {
                Spacer(modifier = Modifier.width(32.dp))
            }

            Text(
                text = genre.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer, CircleShape)
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )

            if (index < movieDetails.genres.size - 1) {
                Spacer(modifier = Modifier.width(12.dp))
            } else {
                Spacer(modifier = Modifier.width(32.dp))
            }
        }
    }
}

@Composable
private fun PlotSummary(movieDetails: MovieDetails) {
    val context = LocalContext.current

    // Plot Summary Title
    Text(
        text = context.getString(R.string.movie_details_plot_summary_title),
        modifier = Modifier.padding(horizontal = 32.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleLarge
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Plot Summary
    Text(
        text = movieDetails.overview.ifBlank {
            context.getString(R.string.movie_details_plot_summary_not_available)
        },
        modifier = Modifier.padding(horizontal = 32.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun CastMembers(castMembers: List<UICastMember>) {
    val context = LocalContext.current

    if (castMembers.isNotEmpty()) {
        Spacer(modifier = Modifier.height(48.dp))

        // Cast Title
        Text(
            text = context.getString(R.string.movie_details_cast_title),
            modifier = Modifier.padding(horizontal = 32.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Cast Members
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(castMembers) { index, member ->
                if (index == 0) {
                    Spacer(modifier = Modifier.width(32.dp))
                }

                CastMemberView(member)

                if (index < castMembers.size - 1) {
                    Spacer(modifier = Modifier.width(28.dp))
                } else {
                    Spacer(modifier = Modifier.width(32.dp))
                }
            }
        }
    }
}

@Composable
private fun CastMemberView(member: UICastMember) {
    Column(modifier = Modifier.width(80.dp)) {
        AsyncImage(
            model = member.photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_unknown_person),
            error = painterResource(id = R.drawable.ic_unknown_person),
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Member Name
        Text(
            text = member.name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Character Name
        Text(
            text = member.characterName,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun RatingBar(viewModel: MovieDetailsViewModel) {
    val state by viewModel.state.collectAsState()

    val viewState = state.viewState
    val movieDetails = state.movieDetails

    AnimatedVisibility(visible = viewState == MovieDetailsViewModelViewState.NORMAL,
        enter = slideInHorizontally(animationSpec = tween(durationMillis = 500)) { fullWidth ->
            fullWidth / 3
        } + fadeIn(
            animationSpec = tween(durationMillis = 500)
        )) {
        Column {
            Spacer(modifier = Modifier.height(245.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                Spacer(modifier = Modifier.width(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 55.dp, bottomStart = 55.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MovieRating(movieDetails)
                    MoviePopularity(movieDetails)
                }
            }
        }
    }

//    if (viewState == MovieDetailsViewModelViewState.NORMAL) {
//        Column {
//            Spacer(modifier = Modifier.height(245.dp))
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(110.dp)
//            ) {
//                Spacer(modifier = Modifier.width(32.dp))
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(RoundedCornerShape(topStart = 55.dp, bottomStart = 55.dp))
//                        .background(MaterialTheme.colorScheme.primaryContainer),
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    MovieRating(movieDetails)
//                    MoviePopularity(movieDetails)
//                }
//            }
//        }
//    }
}

@Composable
private fun MovieRating(movieDetails: MovieDetails) {
    val context = LocalContext.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Palette.LightningYellow),
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        val numberFormatter = NumberFormat.getNumberInstance().apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 0
        }

        Row(verticalAlignment = Alignment.Bottom) {
            // Average Rating
            Text(
                text = numberFormatter.format(movieDetails.voteAverage),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            // Slash
            Text(
                text = context.getString(R.string.movie_details_slash),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            // Out of
            Text(
                text = context.getString(R.string.movie_details_out_of),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Votes
        Text(
            text = movieDetails.voteCount.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun MoviePopularity(movieDetails: MovieDetails) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_up_thin_circle_outline),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Palette.Emerald),
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        val numberFormatter = NumberFormat.getNumberInstance().apply {
            isGroupingUsed = false
            maximumFractionDigits = 2
            minimumFractionDigits = 0
        }

        Text(
            text = numberFormatter.format(movieDetails.popularity),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsPreview() {
    val movie = Movie(
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

    TMDBDemoTheme {
        MovieDetailsScreen(movie)
    }
}

private const val MINUTES_IN_ONE_HOUR = 60