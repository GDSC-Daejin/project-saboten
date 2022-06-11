package commonClient.presentation

import common.model.request.user.UserUpdateRequest
import common.model.reseponse.user.UserInfoResponse
import commonClient.data.LoadState
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.usecase.user.GetUserUseCase
import commonClient.domain.usecase.user.UpdateUserInfoUseCase
import commonClient.presentation.SignupScreenViewModelDelegate.*
import commonClient.utils.toLoadState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

interface SignupScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val userState: LoadState<UserInfoResponse> = LoadState.loading(),
        val updateUserState: LoadState<UserInfoResponse> = LoadState.loading()
    )

    sealed interface Effect

    sealed interface Event {
        class LoadUser(val userId: Long) : Event
        class SaveUser(val updateUserInfo: UserUpdateRequest) : Event
    }
}

@Singleton
class SignupScreenViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : PlatformViewModel(), SignupScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)

    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    private val userState = MutableStateFlow<LoadState<UserInfoResponse>>(LoadState.loading())
    private val updateUserState = MutableStateFlow<LoadState<UserInfoResponse>>(LoadState.loading())

    override val state: StateFlow<State> = combine (
        userState, updateUserState
    ) { userState, updateUserState ->
        State(
            userState = userState,
            updateUserState = updateUserState
        )
    }.asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        when (e) {
            is Event.LoadUser -> {
                getUserUseCase(e.userId)
                    .toLoadState()
                    .onEach { userState.emit(it) }
                    .launchIn(platformViewModelScope)
            }
            is Event.SaveUser -> {
                updateUserInfoUseCase(e.updateUserInfo)
                    .toLoadState()
                    .onEach { updateUserState.emit(it) }
                    .launchIn(platformViewModelScope)
            }
        }
    }
}