package app.saboten.android.ui.screens

open class NavGraphRoute(val route : String)

object AppNavGraph {

    object Login : NavGraphRoute("login")

    object Main : NavGraphRoute("main") {
        object Home : NavGraphRoute("$route/home")
        object Profile : NavGraphRoute("$route/profile")
        object Notifications : NavGraphRoute("$route/notifications")
    }

}