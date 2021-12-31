package components

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import kotlinx.html.UL
import react.RBuilder
import react.router.useLocation
import react.router.useMatch
import styled.StyledDOMBuilder
import styled.css
import styled.styledLi
import styled.styledUl
import styles.Colors

fun StyledDOMBuilder<UL>.Tab(to: String, label: String) {
    val selectedLabel = useLocation()
    styledLi {
        css {
            borderRadius = 8.px
            padding(10.px, 20.px)
            transition(duration = 0.3.s)
            hover {
                backgroundColor = Color("#DDFFED")
            }
        }
        StyledNavLink {
            css {
                textDecoration = TextDecoration.none
                transition(duration = 0.3.s)
                color = if (selectedLabel.pathname == to) Colors.accent else Color.black
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