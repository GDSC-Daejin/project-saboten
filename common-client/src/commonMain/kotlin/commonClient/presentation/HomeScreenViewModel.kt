package commonClient.presentation

import commonClient.data.LoadState
import commonClient.di.HiltViewModel
import commonClient.di.Inject
import commonClient.domain.entity.post.Category
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.presentation.HomeScreenViewModelDelegate.*
import commonClient.utils.toLoadState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface HomeScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val categoriesState: LoadState<List<Category>> = LoadState.Loading(),
    )

    sealed class Effect {

    }

    sealed class Event {
        object ReloadContents : Event()
    }

}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase
) : PlatformViewModel(), HomeScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    private val categoriesState = getCategoriesUseCase().toLoadState()

    override val state: StateFlow<State> = combine(
        categoriesState, flowOf(true)
    ) { categoriesState, _ ->
        State(
            categoriesState = categoriesState
        )
    }.asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        platformViewModelScope.launch {
            when (e) {
                Event.ReloadContents -> {

                }
            }
        }
    }

}