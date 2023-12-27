package br.com.gielamo.tmdb.main.vm

import br.com.gielamo.tmdb.base.vm.BaseViewModel
import br.com.gielamo.tmdb.main.vo.MovieSection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainViewModelState, Unit>() {
    override fun getDefaultState(): MainViewModelState {
        return MainViewModelState(
            sections = listOf(
                MovieSection.NOW_PLAYING, MovieSection.POPULAR,
                MovieSection.TOP_RATED, MovieSection.UPCOMING
            ),
            selectedSection = MovieSection.NOW_PLAYING
        )
    }
}