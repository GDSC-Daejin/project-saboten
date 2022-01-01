import components.*
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import react.*
import react.router.Routes
import routes.home
import routes.login
import routes.route
import styled.css
import styled.styledDiv
import utils.component

private val app = fc<Props> {
    styledDiv {
        css {
            backgroundColor = Color.white
        }
        TopBar {
            Logo()
            TabContainer {
                Tab("/contact", "문의하기")
                Tab("/login", "로그인")
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