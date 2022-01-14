import com.bnorm.react.RFunction
import components.Logo
import components.Tab
import components.TabContainer
import components.TopBar
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import react.RBuilder
import react.router.Routes
import routes.home
import routes.login
import routes.route
import styled.css
import styled.styledDiv

@RFunction
fun RBuilder.App() {
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