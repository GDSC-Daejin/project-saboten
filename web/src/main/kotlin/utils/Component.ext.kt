package utils

import react.FC
import react.Props
import react.RBuilder
import react.RComponent

inline fun <P : Props, reified Component : RComponent<P, *>> RBuilder.component(
    noinline handler: P.() -> Unit
) = child(Component::class) {
    attrs(handler = handler)
}

fun <P : Props> RBuilder.component(
    component: FC<P>,
    handler: P.() -> Unit
) {
    child(component) {
        attrs(handler = handler)
    }
}