package routes

import components.LayoutContainer
import components.MainTitle
import components.Space
import kotlinx.css.px
import react.Props
import react.RBuilder
import react.dom.h2
import react.fc
import utils.component

val profile = fc<Props> {
    LayoutContainer {
        Space(100.px)
        MainTitle("Profile")
    }
}