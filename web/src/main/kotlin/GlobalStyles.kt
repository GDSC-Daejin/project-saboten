import kotlinx.css.*
import styled.Import
import styled.StyleSheet

private val font = Import("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap")

private object GlobalStyles : StyleSheet(name = "GlobalStyles", isStatic = true, imports = listOf(font)) {
    val style by css {
        universal {
            fontFamily = "'Noto Sans KR', sans-serif"
            wordBreak = WordBreak.keepAll
        }

        body {
            margin(0.px)
            padding(0.px)
        }
    }
}

val globalStyle = CssBuilder(allowClasses = false).apply { +GlobalStyles.style }