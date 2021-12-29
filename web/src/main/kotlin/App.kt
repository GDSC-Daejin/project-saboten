import components.TopBar
import kotlinx.css.*
import react.Props
import react.RBuilder
import react.createElement
import react.dom.div
import react.fc
import react.router.Route
import react.router.Routes
import react.router.dom.Link
import routes.Home
import routes.Profile
import styled.css
import styled.styledLi
import styled.styledUl
import utils.component

external interface AppProps : Props {

}

private val app = fc<AppProps> {
    div {
        TopBar {
            styledUl {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                    alignItems = Align.center
                }
                styledLi {
                    css {
                        listStyleType = ListStyleType.none
                        padding(horizontal = 10.px)
                    }
                    Link {
                        attrs.to = "/"
                        +"Home"
                    }
                }
                styledLi {
                    css {
                        listStyleType = ListStyleType.none
                        padding(horizontal = 10.px)
                    }
                    Link {
                        attrs.to = "/profile"
                        +"Profile"
                    }
                }
            }
        }

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