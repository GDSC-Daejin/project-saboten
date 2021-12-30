package components

import kotlinx.css.*
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv


private fun RBuilder.BaseButton(
    style: CssBuilder.() -> Unit,
    icon: StyledDOMBuilder<DIV>.() -> Unit,
    text: StyledDOMBuilder<DIV>.() -> Unit
) {
    styledDiv {
        css {
            style()
            display = Display.flex
            flexDirection = FlexDirection.row
            borderRadius = 10.px
            padding(vertical = 20.px, horizontal = 24.px)
            put("margin-left", "auto")
            put("margin-right", "auto")
        }
        icon()
        text()
    }

}

fun RBuilder.AccentButton(
    style: CssBuilder.() -> Unit = {},
    icon: StyledDOMBuilder<DIV>.() -> Unit = {},
    text: StyledDOMBuilder<DIV>.() -> Unit = {}
) {
    BaseButton(
        style = {
            transition(duration = 0.3.s)
            disabled {
                backgroundColor = Color.lightGray
                color = Color.black
            }
            hover {
                backgroundColor = Color("#357150")
                color = Color.white
            }
            backgroundColor = Color("#45CE84")
            color = Color.white
            style()
        },
        icon, text
    )

}