package routes

import react.Props
import react.RBuilder
import react.dom.h2
import react.fc
import utils.component

external interface HomeProps : Props {

}

private val home = fc<HomeProps> {

    h2 {
        +"Home"
    }

}

fun RBuilder.Home(
    handler : HomeProps.() -> Unit
) {
    component(home, handler)
}