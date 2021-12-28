package routes

import react.Props
import react.RBuilder
import react.dom.h2
import react.fc
import utils.functionalComponent

external interface ProfileProps : Props {

}

private val profile = fc<ProfileProps> {

    h2 {
        +"Profile"
    }

}

fun RBuilder.profile(
    handler : ProfileProps.() -> Unit = {}
) {
    functionalComponent(profile, handler)
}