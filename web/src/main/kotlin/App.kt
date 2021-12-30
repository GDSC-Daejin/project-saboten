import components.*
import react.Props
import react.RBuilder
import react.fc
import react.router.Routes
import react.router.useLocation
import routes.home
import routes.login
import routes.route
import styled.styledDiv
import utils.component

private val app = fc<Props> {
    styledDiv {
        val location = useLocation()
        TopBar {
            Logo()
            TabContainer {
                Tab("/contact", "문의하기", location.pathname == "/contact")
                Tab("/login", "로그인", location.pathname == "/login")
            }
        }
        Routes {
            route("/", home)
            route("/login", login)
        }
    }
}

fun RBuilder.App() {
    component(app)
}