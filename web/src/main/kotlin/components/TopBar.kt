package components

import csstype.Border
import csstype.Color
import csstype.Length
import csstype.LineStyle
import kotlinx.css.*
import react.RBuilder
import react.dom.div
import styled.css
import styled.styledDiv
import styles.Colors
import styles.dividerBoarder

fun RBuilder.TopBar() {
    div {

        styledDiv {
            css {
                height = 69.px
                borderBottom = dividerBoarder
            }
        }

    }
}