
import app.saboten.commonClient.di.initKoin
import kotlinx.browser.document
import org.koin.core.component.KoinComponent
import react.createContext
import react.dom.render
import react.router.dom.BrowserRouter

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
            AppKoinComponentContext.Provider(AppKoinComponent) {
                BrowserRouter {
                    App()
                }
            }
        }
    }
}