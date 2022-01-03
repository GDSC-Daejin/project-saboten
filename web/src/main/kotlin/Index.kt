import commonClient.di.dataModule
import commonClient.di.domainModule
import commonClient.di.initKoin
import commonClient.di.presentationModule
import di.jsKoinModule
import io.ktor.client.engine.js.*
import kotlinx.browser.document
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module
import react.StrictMode
import react.createContext
import react.dom.render
import react.router.dom.BrowserRouter
import styled.injectGlobal

object AppKoinComponent : KoinComponent {
    init {
        startKoin {
            modules(
                jsKoinModule,
                dataModule(),
                domainModule(),
                presentationModule()
            )
        }
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