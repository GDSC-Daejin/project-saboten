package routes

import react.FC
import react.RBuilder
import react.createElement
import react.router.Route

fun RBuilder.route(
    to: String,
    component: FC<*>
) {
    Route {
        attrs.path = to
        attrs.element = createElement { component() }
    }
}