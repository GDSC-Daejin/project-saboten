package commonClient.presentation

import commonClient.data.LoadState
import commonClient.di.HiltViewModel
import commonClient.di.Inject
import commonClient.domain.entity.post.Category
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.presentation.CategoryScreenViewModelDelegate.*
import commonClient.utils.toLoadState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface CategoryScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val categoriesState: LoadState<List<Category>> = LoadState.Loading()
    )

    sealed class Effect {

    }

    sealed class Event {
        object ReloadContents : Event()
    }

}

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase
) : PlatformViewModel(), CategoryScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    private val categoriesState = getCategoriesUseCase().toLoadState()

    override val state: StateFlow<State> = categoriesState.map { categoriesState ->
        State(categoriesState = categoriesState)
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