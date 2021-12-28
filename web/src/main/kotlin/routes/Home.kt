package routes

import react.Props
import react.RBuilder
import react.dom.h2
import react.fc
import utils.functionalComponent

external interface HomeProps : Props {

}

private val home = fc<HomeProps> {

    h2 {
        +"Home"
    }

}

fun RBuilder.home(
    handler : HomeProps.() -> Unit = {}
) {
    functionalComponent(home, handler)
}