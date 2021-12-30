package components

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import kotlinx.html.UL
import react.RBuilder
import styled.StyledDOMBuilder
import styled.css
import styled.styledLi
import styled.styledUl

fun StyledDOMBuilder<UL>.Tab(to: String, label: String, selected: Boolean = false) {
    styledLi {
        css {
            borderRadius = 8.px
            padding(10.px, 20.px)
            transition(duration = 0.3.s)
            hover {
                backgroundColor = Color("#DDFFED")
            }
        }
        StyledLink {
            css {
                textDecoration = TextDecoration.none
                transition(duration = 0.3.s)
                color = if (selected) Color("#45CE84") else Color.black
            }
            attrs.to = to
            +label
        }
    }
}

fun RBuilder.TabContainer(
    tabs: StyledDOMBuilder<UL>.() -> Unit
) {
    styledUl {
        css {
            listStyleType = ListStyleType.none
            display = Display.flex
            flexDirection = FlexDirection.row
            alignItems = Align.center
        }
        tabs()
    }
}