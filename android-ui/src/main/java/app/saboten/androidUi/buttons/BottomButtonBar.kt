package app.saboten.androidUi.buttons

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomButtonBar(
    rightButtonRatio: Float = 0.5f,
    leftButton: (@Composable () -> Unit)? = null,
    rightButton: @Composable () -> Unit,
) {
    Surface {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 15.dp)) {

            if (leftButton != null) {
                Box(modifier = Modifier.weight(1 - rightButtonRatio)) {
                    leftButton()
                }
                Spacer(Modifier.width(10.dp))
            }

            Box(modifier = Modifier.weight(rightButtonRatio)) {
                rightButton()
            }

        }
    }
}