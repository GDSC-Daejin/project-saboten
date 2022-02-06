package app.saboten.androidUi.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

enum class DialogContentGravity {
    Top,
    Bottom
}

@Composable
fun BasicDialog(
    title: String,
    message: String,
    content: (@Composable () -> Unit)? = null,
    dialogContentGravity: DialogContentGravity = DialogContentGravity.Bottom,
    properties: DialogProperties = DialogProperties(),
    onDismissRequest: () -> Unit,
    positiveButton: @Composable () -> Unit,
    negativeButton: (@Composable () -> Unit)? = null,
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(shape = RoundedCornerShape(20.dp)) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(10.dp))
                if (content != null && dialogContentGravity == DialogContentGravity.Top) {
                    content.invoke()
                    Spacer(modifier = Modifier.height(20.dp))
                }
                Text(
                    textAlign = TextAlign.Center,
                    text = title,
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = message,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onSurface.copy(0.3f)
                )
                if (content != null && dialogContentGravity == DialogContentGravity.Bottom) {
                    Spacer(modifier = Modifier.height(20.dp))
                    content()
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    if (negativeButton != null) {
                        Box(modifier = Modifier.weight(1f)) {
                            negativeButton()
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        positiveButton()
                    }
                }
            }
        }
    }

}