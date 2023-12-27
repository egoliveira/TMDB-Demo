package br.com.gielamo.tmdb.section.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gielamo.tmdb.R
import br.com.gielamo.tmdb.domain.vo.Movie
import br.com.gielamo.tmdb.ui.theme.Palette
import coil.compose.AsyncImage
import java.text.NumberFormat
import java.time.LocalDate

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Box(modifier) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = movie.backdropUrl,
            contentDescription = movie.title,
            placeholder = painterResource(id = R.drawable.unknown_movie_backdrop),
            error = painterResource(id = R.drawable.unknown_movie_backdrop),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 8.dp))
                .height(200.dp)
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Palette.Transparent, Palette.Black),
                        startY = size.height / 3,
                        endY = size.height
                    )

                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            brush = gradient,
                            blendMode = BlendMode.Multiply
                        )
                    }
                }
        )

        Column(
            modifier = Modifier
                .height(200.dp)
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = movie.title,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.headlineSmall.copy(color = Palette.White),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val numberFormatter = NumberFormat.getNumberInstance().apply {
                    maximumFractionDigits = 2
                    minimumFractionDigits = 0
                }

                Text(
                    text = numberFormatter.format(movie.voteAverage),
                    style = MaterialTheme.typography.titleMedium.copy(color = Palette.White)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Palette.LightningYellow),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewMovieItem() {
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

    MovieItem(movie = movie)
}
