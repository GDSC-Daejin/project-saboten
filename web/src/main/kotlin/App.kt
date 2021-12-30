import components.TopBar
import kotlinx.css.*
import react.Props
import react.RBuilder
import react.fc
import react.router.Routes
import routes.home
import routes.profile
import routes.route
import styled.css
import styled.styledDiv
import utils.component

private val app = fc<Props> {
    styledDiv {
        TopBar()
        Routes {
            route("/", home)
            route("/profile", profile)
        }
    }
}

fun RBuilder.App() {
    component(app)
}