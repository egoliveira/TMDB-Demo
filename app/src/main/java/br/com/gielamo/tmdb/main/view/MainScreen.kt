package br.com.gielamo.tmdb.main.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.gielamo.tmdb.R
import br.com.gielamo.tmdb.main.vm.MainViewModel
import br.com.gielamo.tmdb.main.vo.MovieSection
import br.com.gielamo.tmdb.section.view.NowPlayingMoviesList
import br.com.gielamo.tmdb.section.view.PopularMoviesList
import br.com.gielamo.tmdb.section.view.TopRatedMoviesList
import br.com.gielamo.tmdb.section.view.UpcomingMoviesList
import br.com.gielamo.tmdb.ui.theme.TMDBDemoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.app_name))
                }
            )
        }) {
        MainScreenContent(it)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreenContent(
    paddingValues: PaddingValues,
    viewModel: MainViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column {
            val pagerState = rememberPagerState(pageCount = { state.sections.size })
            var tabRowSelectedIndex by remember { mutableIntStateOf(0) }

            ScrollableTabRow(
                selectedTabIndex = tabRowSelectedIndex,
                indicator = { tabPositions ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .tabIndicatorOffset(tabPositions[tabRowSelectedIndex])
                            .height(6.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = MaterialTheme.colorScheme.tertiary)
                    )
                },
                divider = {},
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                state.sections.forEachIndexed { index, movieSection ->
                    val selected = tabRowSelectedIndex == index
                    Tab(
                        selected = selected,
                        onClick = {
                            tabRowSelectedIndex = index

                            scope.launch {
                                if (pagerState.currentPage != index) {
                                    pagerState.scrollToPage(index)
                                }
                            }
                        },
                        text = {
                            Text(
                                text = getMovieSectionName(movieSection),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxHeight()
            ) { pageIndex ->
                when (state.sections[pageIndex]) {
                    MovieSection.NOW_PLAYING -> NowPlayingMoviesList()
                    MovieSection.POPULAR -> PopularMoviesList()
                    MovieSection.TOP_RATED -> TopRatedMoviesList()
                    MovieSection.UPCOMING -> UpcomingMoviesList()
                }
            }

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect {
                    if (tabRowSelectedIndex != it) {
                        tabRowSelectedIndex = it
                    }
                }
            }
        }
    }
}

@Composable
private fun getMovieSectionName(movieSection: MovieSection): String {
    return when (movieSection) {
        MovieSection.NOW_PLAYING -> stringResource(R.string.main_activity_movie_section_now_playing)
        MovieSection.POPULAR -> stringResource(R.string.main_activity_movie_section_popular)
        MovieSection.TOP_RATED -> stringResource(R.string.main_activity_movie_top_rated)
        MovieSection.UPCOMING -> stringResource(R.string.main_activity_movie_upcoming)
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    TMDBDemoTheme {
        MainScreen()
    }
}