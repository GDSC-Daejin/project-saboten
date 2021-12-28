import react.Props
import react.RBuilder
import react.createElement
import react.dom.div
import react.dom.li
import react.dom.ul
import react.fc
import react.router.Route
import react.router.Routes
import react.router.dom.Link
import routes.home
import routes.profile
import utils.functionalComponent

external interface AppProps : Props {

}

private val app = fc<AppProps> {
    div {

        ul {
            li {
                Link {
                    attrs.to = "/"
                    +"Home"
                }
            }
            li {
                Link {
                    attrs.to = "/profile"
                    +"Profile"
                }
            }
        }

        Routes {

            Route {
                attrs.path = "/"
                attrs.element = createElement { home() }
            }

            Route {
                attrs.path = "/profile"
                attrs.element = createElement { profile() }
            }

        }
    }
}

fun RBuilder.app(
    handler : AppProps.() -> Unit = {}
) {
    functionalComponent(app, handler)
}