package app.saboten.androidUi.bars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.buttons.TextOnlyButton

@Composable
fun HeaderBar(
    title: String,
    moreButtonText: String? = null,
    moreButtonAction: () -> Unit = {},
) {

    Surface {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, bottom = 20.dp, top = 20.dp, end = 10.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
            if (moreButtonText != null) {
                TextOnlyButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = moreButtonText, onClick = moreButtonAction
                )
            }
        }
    }

}