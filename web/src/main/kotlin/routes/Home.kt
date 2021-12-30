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
            Space(200.px)
            MainTitle("지금 진행중인\n밸런스 게임")
            Space(20.px)
            SubTitle("다양한 사람들과 소통해보세요.")
        }
    }
}