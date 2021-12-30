package components

import kotlinx.css.*
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv
import styles.dividerBoarder

fun RBuilder.TopBar(
    content: StyledDOMBuilder<DIV>.() -> Unit
) {
    styledDiv {
        css {
            width = 100.pct
            position = Position.fixed
            zIndex = 999
            borderBottom = dividerBoarder
            backgroundColor = Color.white
        }

        styledDiv {
            css {
                display = Display.flex
                flexGrow = 1.0
                flexBasis = FlexBasis.zero
                width = 100.pct
                height = 70.px
                minWidth = 320.px
                maxWidth = 1100.px
                justifyContent = JustifyContent.spaceBetween
                margin(all = 0.px)
                put("margin-left", "auto")
                put("margin-right", "auto")
            }

            content()
        }
    }
}