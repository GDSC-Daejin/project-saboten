package routes

import app.saboten.commonClient.presentation.HomeScreenViewModel
import components.Space
import kotlinx.css.px
import react.Props
import react.RBuilder
import react.dom.h2
import utils.component
import utils.extract
import utils.vfc

external interface HomeProps : Props {

}

private val home = vfc<HomeProps, HomeScreenViewModel> { _, vm ->
    val (state, effect, event) = vm.extract()

    Space(100.px)
    h2 {
        +"Home ${state?.me}"
    }

}

fun RBuilder.Home(
    handler: (HomeProps) -> Unit = {}
) {
    component(home, handler)
}