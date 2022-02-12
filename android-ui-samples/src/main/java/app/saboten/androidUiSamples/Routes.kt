package app.saboten.androidUiSamples

sealed class UiSamplesAppRoutes(val route : String) {

    object Home : UiSamplesAppRoutes("home")

    object Lists : UiSamplesAppRoutes("lists")

    object Buttons : UiSamplesAppRoutes("buttons")

    object TextFields : UiSamplesAppRoutes("textfields")

    object Dialogs : UiSamplesAppRoutes("dialogs")

    object Typographies : UiSamplesAppRoutes("typographies")

    object Colors : UiSamplesAppRoutes("colors")

}