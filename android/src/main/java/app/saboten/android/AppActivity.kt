package app.saboten.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.saboten.android.ui.SabotenAppScreen
import app.saboten.android.ui.styles.MainTheme

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MainTheme {
                SabotenAppScreen()
            }

        }
    }

}