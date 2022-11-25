package app.saboten.androidApp.ui.screens.main

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.image.sabotenlogo.SabotenLogo
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.styles.SabotenColors

@Composable
fun MainTopBar(
    onNotificationClicked: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
) {

    BasicTopBar(
        title = {
            Icon(
                imageVector = SabotenLogo.SabotenLogo,
                contentDescription = "선인장 로그",
                tint = SabotenColors.green500
            )
        },
        actions = {
            IconButton(onClick = { onNotificationClicked() }) {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "알림",
                    modifier = Modifier.size(26.dp),
                    tint = SabotenColors.green500
                )

            }
            IconButton(onClick = { onSearchClicked() }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "검색",
                    modifier = Modifier.size(26.dp),
                    tint = SabotenColors.green500
                )
            }
        }
    )
}
