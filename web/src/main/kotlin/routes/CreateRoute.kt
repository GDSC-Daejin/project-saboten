package routes

import react.FC
import react.Props
import react.RBuilder
import react.createElement
import react.router.Route

fun <P : Props> RBuilder.route(
    to: String,
    component: FC<P>
) {
    Route {
        attrs.path = to
        attrs.element = createElement(component)
    }
}