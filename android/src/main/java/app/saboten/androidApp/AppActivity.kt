package app.saboten.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.saboten.androidApp.ui.screens.AppScreen
import app.saboten.androidUi.styles.MainTheme

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                AppScreen()
            }
        }
    }

}