package app.saboten.androidApp.ui.screens.main

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.image.sabotenlogo.MainLogo
import app.saboten.androidUi.image.sabotenlogo.SabotenIcons
import app.saboten.androidUi.styles.MainTheme

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    onSearchClicked: () -> Unit = {},
) {
    BasicTopBar(
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        title = {
            CompositionLocalProvider(LocalContentAlpha provides 1f) {
                Icon(
                    imageVector = SabotenIcons.MainLogo,
                    contentDescription = "선인장 로고"
                )
            }

        },
        actions = {
            CompositionLocalProvider(LocalContentAlpha provides 1f) {
                IconButton(onClick = { onSearchClicked() }) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "검색",
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    )
}

@Composable
@Preview
fun MainTopBarPreview() {
    MainTheme {
        MainTopBar()
    }
}