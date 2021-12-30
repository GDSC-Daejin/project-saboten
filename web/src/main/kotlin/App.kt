import components.TabBar
import react.Props
import react.RBuilder
import react.createElement
import react.dom.div
import react.fc
import react.router.Route
import react.router.Routes
import routes.Home
import routes.Profile
import utils.component

external interface AppProps : Props {

}

private val app = fc<AppProps> {

    div {

        Routes {

            Route {
                attrs.path = "/"
                attrs.element = createElement { Home() }
            }

            Route {
                attrs.path = "/profile"
                attrs.element = createElement { Profile() }
            }

        }
    }
}

fun RBuilder.App(
    handler: (AppProps) -> Unit = {}
) {
    component(app, handler)
}