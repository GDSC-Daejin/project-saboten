package app.saboten.androidApp.ui.screens.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Casino
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.MainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun SignupScreen(
    navigator: DestinationsNavigator
) {
    BasicScaffold(
        topBar = {
            BasicTopBar(
                title = { TODO("signup top bar title")},
                navigationIcon = {
                    ToolbarBackButton {
                        navigator.popBackStack()
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colors.onPrimary
    ) {
        SignupScreenContent()
    }
}

@Composable
private fun SignupScreenContent(
    modifier: Modifier = Modifier
) {
    var nickname by remember { mutableStateOf("") }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "계정을 생성하세요",
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(37.dp))

            NetworkImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                "https://picsum.photos/200"
            )

            Spacer(modifier = Modifier.height(47.dp))

            TextField(
                value = nickname,
                onValueChange = { nickname = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                placeholder = { Text(text = "닉네임") },
                trailingIcon = {
                    Icon(
                        Icons.Rounded.Casino,
                        contentDescription = "setting random nickname",
                        modifier = Modifier.clickable {
                            TODO("random nickname")
                        }
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.onPrimary
                ),
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                text = "10자",
                textAlign = TextAlign.End
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /*TODO("signup next button")*/ },
                modifier = Modifier
                    .height(40.dp)
                    .width(163.dp)
            ) {
                Text(text = "다음")
            }

            Spacer(modifier = Modifier.height(78.dp))

            Surface(
                modifier = Modifier.size(width = 163.dp, height = 5.dp),
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
            ) {}

            Spacer(modifier = Modifier.height(17.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenContentPreview() {
    MainTheme {
        SignupScreenContent()
    }
}