package components

import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import kotlinx.html.DIV
import react.RBuilder
import react.dom.onClick
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv


private fun RBuilder.BaseButton(
    style: CssBuilder.() -> Unit,
    icon: StyledDOMBuilder<DIV>.() -> Unit,
    text: StyledDOMBuilder<DIV>.() -> Unit,
    onClick: () -> Unit,
) {
    styledDiv {
        attrs.onClick = { onClick() }
        css {
            style()
            display = Display.flex
            flexDirection = FlexDirection.row
            borderRadius = 10.px
            padding(vertical = 20.px, horizontal = 24.px)
            put("margin-left", "auto")
            put("margin-right", "auto")
            hover {
                cursor = Cursor.pointer
            }
        }
        icon()
        text()
    }

}

fun RBuilder.AccentButton(
    style: CssBuilder.() -> Unit = {},
    icon: StyledDOMBuilder<DIV>.() -> Unit = {},
    text: StyledDOMBuilder<DIV>.() -> Unit = {},
    onClick: () -> Unit,
) {
    ColoredButton(
        ColoredButtonStyle.accent,
        style,
        icon, text,
        onClick,
    )

}

class ColoredButtonStyle(
    val default: Pair<Color, Color>,
    val hover: Pair<Color, Color>,
    val disabled: Pair<Color, Color>
) {

    companion object {
        val accent = ColoredButtonStyle(
            Color("#45CE84") to Color.white,
            Color("#357150") to Color.white,
            Color.lightGray to Color.black,
        )

        val white = ColoredButtonStyle(
            Color.white to Color.black,
            Color.lightGray to Color.black,
            Color.lightGray to Color.black,
        )

        val facebook = ColoredButtonStyle(
            Color("#4267B2") to Color.white,
            Color("#2D4A84") to Color.white,
            Color.lightGray to Color.black,
        )
    }

}

fun RBuilder.ColoredButton(
    buttonStyle: ColoredButtonStyle,
    style: CssBuilder.() -> Unit = {},
    icon: StyledDOMBuilder<DIV>.() -> Unit = {},
    text: StyledDOMBuilder<DIV>.() -> Unit = {},
    onClick: () -> Unit,
) {
    BaseButton(
        style = {
            transition(duration = 0.3.s)
            disabled {
                backgroundColor = buttonStyle.disabled.first
                color = buttonStyle.disabled.second
            }
            hover {
                backgroundColor = buttonStyle.hover.first
                color = buttonStyle.hover.second
            }
            border(1.px, BorderStyle.solid, Color("#F0F0F0"), 10.px)
            backgroundColor = buttonStyle.default.first
            color = buttonStyle.default.second
            alignContent = Align.center
            alignItems = Align.center
            style()
        },
        icon, text,
        onClick
    )

}