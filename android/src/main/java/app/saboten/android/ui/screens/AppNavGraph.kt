package app.saboten.android.ui.screens

open class NavGraphRoute(val route : String)

object AppNavGraph {

    object Login : NavGraphRoute("login")

    object Home : NavGraphRoute("home") {
        object Main : NavGraphRoute("$route/main")
        object Profile : NavGraphRoute("$route/profile")
        object Notifications : NavGraphRoute("$route/notifications")
    }

}