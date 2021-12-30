package components

import kotlinx.css.*
import kotlinx.html.DIV
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

fun RBuilder.LayoutContainer(content : (StyledDOMBuilder<DIV>.() -> Unit)) {

    styledDiv {
        css {
            position = Position.relative
            width = 100.pct
            minWidth = 320.px
            maxWidth = 1100.px
            margin(all = 0.px)
            put("margin-left", "auto")
            put("margin-right", "auto")
        }

        content()
    }

}

fun RBuilder.InnerContainer(content : (StyledDOMBuilder<DIV>.() -> Unit)) {

    styledDiv {
        css {
            padding(horizontal = 4.pct)
            width = 92.pct
        }

        content()
    }

}