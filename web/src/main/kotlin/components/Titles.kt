package components

import kotlinx.css.*
import react.RBuilder
import styled.css
import styled.styledDiv
import styles.MAX_WIDTH_LANDSCAPE_TABLETS
import styles.MAX_WIDTH_PORTRAIT_TABLETS
import styles.MAX_WIDTH_SMALL_PHONE
import styles.mediaMaxWidth

fun RBuilder.MainTitle(text: String) {
    styledDiv {
        css {
            fontSize = 48.px
            fontWeight = FontWeight.bolder

            color = Color("#333c4b")

            mediaMaxWidth(
                MAX_WIDTH_SMALL_PHONE,
                MAX_WIDTH_PORTRAIT_TABLETS,
            ) {
                fontSize = 36.px
            }
        }
        +text
    }
}

fun RBuilder.SubTitle(text: String) {
    styledDiv {
        css {
            fontSize = 24.px
            fontWeight = FontWeight.normal

            color = Color("#333c4b")

            mediaMaxWidth(
                MAX_WIDTH_SMALL_PHONE,
                MAX_WIDTH_PORTRAIT_TABLETS,
            ) {
                fontSize = 16.px
            }
        }
        +text
    }
}