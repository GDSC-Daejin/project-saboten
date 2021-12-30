package routes

import components.*
import kotlinx.css.*
import react.Props
import react.dom.img
import react.fc
import styled.css
import styled.styledDiv

val login = fc<Props> {
    LayoutContainer {
        InnerContainer {
            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    alignItems = Align.center
                }
                Space(100.px)
                MainTitle("로그인")
                Space(10.px)
                SubTitle("아래 로그인 방법으로 로그인해주세요.")
                Space(20.px)
                AccentButton(
                    style = { minWidth = 200.px },
                    text = { ButtonText("이메일로 로그인") }
                )
            }
        }
    }
}