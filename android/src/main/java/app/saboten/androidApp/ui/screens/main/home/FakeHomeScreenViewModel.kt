package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import commonClient.data.LoadState
import commonClient.domain.entity.post.Category
import commonClient.presentation.HomeScreenViewModelDelegate
import commonClient.utils.toLoadState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

@Composable
fun fakeHomeScreenViewModel() : HomeScreenViewModelDelegate {

    val coroutineScope = rememberCoroutineScope()

    return remember {
        object : HomeScreenViewModelDelegate {
            private val effectChannel = Channel<HomeScreenViewModelDelegate.Effect>(Channel.UNLIMITED)
            override val effect: Flow<HomeScreenViewModelDelegate.Effect> = effectChannel.receiveAsFlow()

            private val categoriesState  = MutableStateFlow<LoadState<List<Category>>>(LoadState.Loading())

            override val state: StateFlow<HomeScreenViewModelDelegate.State> = combine(
                categoriesState, flowOf(true)
            ) { categoriesState, _ ->
                HomeScreenViewModelDelegate.State(
                    categoriesState = categoriesState
                )
            }.asStateFlow(HomeScreenViewModelDelegate.State(), coroutineScope)

            override fun event(e: HomeScreenViewModelDelegate.Event) {

            }

            init {
                coroutineScope.launch {
                    categoriesState.emit(LoadState.Loading())
                    delay(1000L)
                    categoriesState.emit(LoadState.Success(
                        (0..10).map {
                            Category(
                                id = 1,
                                name = "Category $it",
                                iconUrl = "https://github.com/GDSC-Daejin/project-saboten-iconpack/blob/master/ic_favorite.svg"
                            )
                        }
                    ))
                }
            }

        }
    }
}
