package app.saboten.androidApp.ui.screens.main.post.write

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.HeaderBar
import app.saboten.androidUi.buttons.FilledButton
import app.saboten.androidUi.styles.SabotenColors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.presentation.post.WritePostScreenEffect
import commonClient.presentation.post.WritePostScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
@Destination
fun WritePostScreen(
    navigator: DestinationsNavigator,
) {

    val viewModel = koinViewModel<WritePostScreenViewModel>()

    WritePostScreenContent(viewModel) {
        navigator.popBackStack()
    }
}

@Composable
fun WritePostScreenContent(
    viewModel: WritePostScreenViewModel,
    onBackPressed: () -> Unit,
) {

    val context = LocalContext.current

    val state by viewModel.collectAsState()

    var isPosting by remember { mutableStateOf(false) }

    val titleText = remember {
        mutableStateOf("")
    }
    val firstTopicText = remember {
        mutableStateOf("")
    }
    val secondTopicText = remember {
        mutableStateOf("")
    }

    viewModel.collectSideEffect {
        when (it) {
            is WritePostScreenEffect.CreatePosing -> {
                isPosting = true
            }

            is WritePostScreenEffect.CreatePostFailed -> {
                isPosting = false
            }

            is WritePostScreenEffect.CreatePosted -> {
                onBackPressed()
            }
            is WritePostScreenEffect.UnSelectedCategory -> {
                Toast.makeText(context, "카테고리를 1개 이상 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

        }
    }

    Scaffold(
        bottomBar = {
            Column(modifier = Modifier.navigationBarsPadding()) {

                FilledButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    enabled = isPosting.not() && titleText.value.isNotBlank() && firstTopicText.value.isNotBlank() && secondTopicText.value.isNotBlank(),
                    backgroundColor = SabotenColors.green500,
                    onClick = {
                        viewModel.createPost(
                            titleText.value,
                            firstTopicText.value,
                            secondTopicText.value
                        )
                    },
                    text = "등록하기"
                )

            }
        },
        topBar = {
            BasicTopBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(Icons.Rounded.Close, null)
                    }
                },
            )
        }
    ) {

        if (isPosting) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            }
        }

        LazyColumn {

            item {

                Column(modifier = Modifier.padding(it)) {

                    Column(modifier = Modifier.padding(top = 20.dp)) {

                        // TODO: 디테일한 패딩 조정 필요.
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = titleText.value,
                            onValueChange = { textValue ->
                                titleText.value = textValue
                            },
                            placeholder = { Text("제목을 입력하세요.", color = SabotenColors.grey500) },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                cursorColor = Color.Black,
                                disabledLabelColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                        )

                        WriteTopicTextField(firstTopicText.value) { firstTopicTextValue ->
                            firstTopicText.value = firstTopicTextValue
                        }

                        Spacer(modifier = Modifier.padding(top = 10.dp))

                        WriteTopicTextField(secondTopicText.value, false) { secondTopicTextValue ->
                            secondTopicText.value = secondTopicTextValue
                        }

                        Spacer(modifier = Modifier.padding(top = 20.dp))
                        HeaderBar(title = "카테고리 선택")

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                        ) {

                            items(state.categories.getDataOrNull() ?: emptyList()) { item ->
                                val itemId = item.id
                                Surface(
                                    onClick = { viewModel.selectCategory(itemId) },
                                    color = if (itemId in state.selectedCategoryIds) MaterialTheme.colors.secondary
                                    else Color.Transparent,
                                    contentColor = if (itemId in state.selectedCategoryIds) MaterialTheme.colors.onSecondary
                                    else MaterialTheme.colors.onBackground,
                                    shape = CircleShape,
                                    border = if (itemId in state.selectedCategoryIds) null
                                    else BorderStroke(1.dp, MaterialTheme.colors.onBackground),
                                ) {
                                    Text(
                                        text = item.name,
                                        modifier = Modifier.padding(10.dp),
                                    )

                                }
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                    }


                }

            }
        }
    }
}

@Composable
fun WriteTopicTextField(
    value: String,
    isFirst: Boolean = true,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = SabotenColors.grey500,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .background(SabotenColors.grey400),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isFirst) "A" else "B",
                fontSize = 12.sp,
                color = Color.White
            )
        }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { textValue ->
                onValueChange(textValue)
            },
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${if (isFirst) "A" else "B"} 선택지를 입력하세요.",
                    textAlign = TextAlign.Center,
                    color = SabotenColors.grey500
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                disabledLabelColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
    }
}
