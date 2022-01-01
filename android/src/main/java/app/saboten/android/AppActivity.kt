package app.saboten.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.saboten.android.ui.screens.AppScreen
import app.saboten.android.ui.styles.MainTheme
import common.entities.User

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