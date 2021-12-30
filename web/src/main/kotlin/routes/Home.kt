package routes

import app.saboten.commonClient.presentation.HomeScreenViewModel
import components.*
import kotlinx.css.px
import react.Props
import react.RBuilder
import react.dom.h2
import utils.component
import utils.extract
import utils.vfc

val home = vfc<Props, HomeScreenViewModel> { _, vm ->
    val (state, effect, event) = vm.extract()
    LayoutContainer {
        InnerContainer {
            Space(100.px)
            MainTitle("Home")
            Space(20.px)
            SubTitle("This is Subtitle")
        }
    }
}