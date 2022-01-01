package utils

import commonClient.presentation.PlatformViewModel
import react.Props
import react.RBuilder
import react.fc
import react.useEffectOnce

inline fun <P : Props, reified VM : PlatformViewModel> vfc(
    crossinline func: RBuilder.(props: P, viewModel: VM) -> Unit
) = fc<P> {
    val vm by inject<VM>()
    func(it, vm)
    useEffectOnce {
        cleanup { vm.onViewModelCleared() }
    }
}