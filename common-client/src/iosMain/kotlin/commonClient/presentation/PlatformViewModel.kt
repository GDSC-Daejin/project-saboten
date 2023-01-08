package commonClient.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.SettingsBuilder
import org.orbitmvi.orbit.container

actual abstract class PlatformViewModel<State : Any, SideEffect: Any> actual constructor() : ContainerHost<State, SideEffect> {
    actual open val platformViewModelScope: CoroutineScope = MainScope()

    actual open fun onViewModelCleared() {
    }

}

actual fun <STATE : Any, SIDE_EFFECT : Any> PlatformViewModel<STATE, SIDE_EFFECT>.container(
    initialState: STATE,
    buildSettings: SettingsBuilder.() -> Unit,
    onCreate: ((state: STATE) -> Unit)?,
): Container<STATE, SIDE_EFFECT> = platformViewModelScope.container(initialState, buildSettings, onCreate)