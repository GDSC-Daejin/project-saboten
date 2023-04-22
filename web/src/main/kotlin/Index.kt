import commonClient.di.initKoin
import commonClient.logger.ClientLogger
import commonClient.utils.ClientProperties
import kotlinx.browser.document
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import react.StrictMode
import react.createContext
import react.dom.link
import react.dom.render
import react.router.dom.BrowserRouter
import styled.injectGlobal

object AppKoinComponent : KoinComponent {

    private val clientProperties by inject<ClientProperties>()

    init {
        initKoin()
        ClientLogger.init(clientProperties)
    }
}

val AppKoinComponentContext = createContext<AppKoinComponent>()

fun main() {
    val root = document.getElementById("root")
    root?.let {
        render(it) {
            link(href = "https://fonts.googleapis.com/icon?family=Material+Icons", "stylesheet") {}
            injectGlobal(globalStyle)
            AppKoinComponentContext.Provider(AppKoinComponent) {
                StrictMode {
                    BrowserRouter {
                        App()
                    }
                }
            }
        }
    }
}