package components

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.RBuilder
import styled.css
import styled.styledDiv


fun RBuilder.Logo() {
    StyledLink {
        css {
            textDecoration = TextDecoration.none
            color = Color.black
            fontSize = 28.px
            fontWeight = FontWeight.normal
            display = Display.flex
            alignItems = Align.center
            color = Color("#333c4b")
            padding(20.px)
        }
        attrs.to = "/"
        +"Saboten"
    }
}