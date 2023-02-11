package app.saboten.androidApp.ui.screens.main.post.write

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.styles.SabotenColors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.presentation.main.WritePostScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@Destination
fun WritePostScreen(
    navigator: DestinationsNavigator,
) {

    val viewModel = koinViewModel<WritePostScreenViewModel>()

    WritePostScreenContent(viewModel)
}

@Composable
fun WritePostScreenContent(
    viewModel: WritePostScreenViewModel
) {

    val state by viewModel.collectAsState()

    Column() {

        val titleText = remember {
            mutableStateOf("")
        }
        val firstTopicText = remember {
            mutableStateOf("")
        }
        val secondTopicText = remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .background(SabotenColors.grey100)
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp)
        ) {

            // TODO: 디테일한 패딩 조정 필요.
            TextField(
                value = titleText.value,
                onValueChange = { textValue ->
                    titleText.value = textValue
                },
                placeholder = { Text("제목을 입력하세요.", color = SabotenColors.grey500) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = SabotenColors.grey100,
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
            Text(text = "카테고리 선택")

            // TODO: 기본으로 잡힌 패딩 삭제 / 다중 선택 지원
            LazyRow(
                contentPadding = PaddingValues(20.dp),
            ) {

                items(state.categories.getDataOrNull() ?: emptyList()) { item ->
                    val itemId = if (item.id < 0) null else item.id
                    Surface(
                        onClick = { viewModel.selectCategory(itemId) },
                        color = if (state.selectedCategoryId == itemId) MaterialTheme.colors.secondary
                        else Color.Transparent,
                        contentColor = if (state.selectedCategoryId == itemId) MaterialTheme.colors.onSecondary
                        else MaterialTheme.colors.onBackground,
                        shape = CircleShape,
                        border = if (state.selectedCategoryId == itemId) null
                        else {
                            BorderStroke(1.dp, MaterialTheme.colors.onBackground)
                        },
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

        // TODO: 화면 맨 아래에 두고, 네비게이션 바의 크기만큼 패딩 주기
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            SabotenColors.green500,
                            Color(0xFF20AE6C)
                        )
                    )
                ),
            onClick = {
                viewModel.createPost(
                    titleText.value,
                    firstTopicText.value,
                    secondTopicText.value
                )
            },
        ) {
            Text(text = "등록하기")
        }


    }
}

@Composable
fun WriteTopicTextField(
    value: String,
    isFirst: Boolean = true,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
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
                backgroundColor = SabotenColors.grey100,
                cursorColor = Color.Black,
                disabledLabelColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
    }
}
