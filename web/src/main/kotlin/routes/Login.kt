package routes

import components.*
import kotlinx.css.px
import react.Props
import react.fc

val login = fc<Props> {
    LayoutContainer {
        InnerContainer {
            Space(100.px)
            MainTitle("로그인")
            Space(20.px)
            SubTitle("로그인해주세요.")
        }
    }
}