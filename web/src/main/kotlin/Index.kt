import kotlinx.browser.document
import react.dom.render
import react.router.dom.BrowserRouter

fun main() {
    val root = document.getElementById("root")
    root?.let {
        render(it) {
            BrowserRouter {
                app()
            }
        }
    }
}