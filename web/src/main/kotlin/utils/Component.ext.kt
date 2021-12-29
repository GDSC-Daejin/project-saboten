package utils

import AppKoinComponentContext
import app.saboten.commonClient.presentation.PlatformViewModel
import org.koin.core.component.inject
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools
import react.FC
import react.Props
import react.RBuilder
import react.useContext

fun <P : Props> RBuilder.component(
    component: FC<P>,
    handler: (P) -> Unit
) {
    child(component) {
        attrs(handler = handler)
    }
}

inline fun <reified T : Any> RBuilder.inject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
    noinline parameters: ParametersDefinition? = null
) = useContext(context = AppKoinComponentContext).inject<T>(
    qualifier, mode, parameters
)