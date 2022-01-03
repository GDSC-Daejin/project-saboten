package routes

import components.*
import kotlinx.css.*
import react.Props
import react.dom.img
import react.dom.span
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
                Space(30.px)
                ColoredButton(
                    buttonStyle = ColoredButtonStyle.white,
                    style = { minWidth = 300.px },
                    icon = {
                        img(src = "https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg") {}
                    },
                    text = { ButtonText("구글 이메일로 로그인") }
                ) {

                }
                Space(10.px)
                ColoredButton(
                    buttonStyle = ColoredButtonStyle.facebook,
                    style = { minWidth = 300.px },
                    icon = {
                        span(classes = "material-icons md-24") { +"facebook" }
                    },
                    text = { ButtonText("Facebook 으로 로그인") }
                ) {

                }
                Space(10.px)
                AccentButton(
                    style = { minWidth = 300.px },
                    icon = {
                        span(classes = "material-icons md-24") { +"email" }
                    },
                    text = { ButtonText("이메일로 로그인") }
                ) {

                }
            }
        }
    }
}