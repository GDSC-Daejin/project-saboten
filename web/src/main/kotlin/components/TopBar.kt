package components

import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.TopBar(nestedContent: StyledDOMBuilder<DIV>.() -> Unit) {
    styledDiv {
        css {
            margin(all = 0.px)
            height = 70.px
            display = Display.flex
            overflow = Overflow.hidden
            backgroundColor = Color.white
            boxShadow(Color.lightGray, offsetY = 10.px, blurRadius = 10.px)
        }

        nestedContent()
    }
}