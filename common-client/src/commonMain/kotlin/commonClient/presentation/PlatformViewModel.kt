package commonClient.presentation

import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder

expect abstract class PlatformViewModel<State : Any, SideEffect: Any>() : ContainerHost<State, SideEffect> {

    open val platformViewModelScope : CoroutineScope

    open fun onViewModelCleared()

}

expect fun <STATE : Any, SIDE_EFFECT : Any> PlatformViewModel<STATE, SIDE_EFFECT>.container(
    initialState: STATE,
    buildSettings: SettingsBuilder.() -> Unit = {},
    onCreate: ((state: STATE) -> Unit)? = null
): Container<STATE, SIDE_EFFECT>