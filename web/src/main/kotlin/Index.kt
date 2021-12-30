import app.saboten.commonClient.di.initKoin
import kotlinx.browser.document
import org.koin.core.component.KoinComponent
import react.StrictMode
import react.createContext
import react.dom.render
import react.dom.style
import react.router.dom.BrowserRouter
import styled.injectGlobal

object AppKoinComponent : KoinComponent {
    init {
        initKoin()
    }
}

val AppKoinComponentContext = createContext<AppKoinComponent>()

fun main() {
    val root = document.getElementById("root")
    root?.let {
        render(it) {
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