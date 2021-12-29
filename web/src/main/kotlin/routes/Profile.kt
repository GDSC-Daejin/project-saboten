package routes

import react.Props
import react.RBuilder
import react.dom.h2
import react.fc
import utils.component

external interface ProfileProps : Props {

}

private val profile = fc<ProfileProps> {

    h2 {
        +"Profile"
    }

}

fun RBuilder.Profile(
    handler : (ProfileProps) -> Unit = {}
) {
    component(profile, handler)
}