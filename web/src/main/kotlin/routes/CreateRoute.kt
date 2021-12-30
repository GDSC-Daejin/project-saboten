package routes

import react.FC
import react.RBuilder
import react.createElement
import react.router.Route
import utils.component

fun RBuilder.route(
    to: String,
    component: FC<*>
) {
    Route {
        attrs.path = to
        attrs.element = createElement { component(component) }
    }
}