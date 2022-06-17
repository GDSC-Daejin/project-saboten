package app.saboten.androidApp.ui.screens.post

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.MainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

private val initialBackgroundColorOfA = mutableStateOf(Color(0xFFC2C2C2))
private val initialBackgroundColorOfB = mutableStateOf(Color(0xFFC2C2C2))

private var postTitle = mutableStateOf("")
private val postOptionA = mutableStateOf("")
private val postOptionB = mutableStateOf("")

private val categoryState = listOf("전체", "인기", "연애", "옷차림", "취미", "음식", "취향")
private val selectedCategory = mutableStateOf(categoryState[0])
private val dropdownMenu = mutableStateOf("추가 옵션")
private val dropdowmMenuList = listOf("선택 안함", "여성만", "남성만")

private val colorTransparent = Color.Transparent

private val gradientColorList = listOf(
    Color(0xFFCF4B4B),
    Color(0xFFFF6B00),
    Color(0xFFFFF500),
    Color(0xFF20AC1D),
    Color(0xFF00A3FF)
)

private val colorList = listOf(
    Color(0xFFFFFFFF),
    Color(0xFF000000),
    Color(0xFF57ADFF),
    Color(0xFF61E52A),
    Color(0xFFFFB91D),
    Color(0xFFFF7A00),
    Color(0xFFE82582),
    Color(0xFF0038FF),
    Color(0xFF8F00FF),
    Color(0xFF00A3FF),
    Color(0xFFFFFFFF),
    Color(0xFF000000),
    Color(0xFF57ADFF),
    Color(0xFF61E52A),
    Color(0xFFFFB91D),
    Color(0xFFFF7A00),
    Color(0xFFE82582),
    Color(0xFF0038FF),
    Color(0xFF8F00FF),
    Color(0x00000000),
)

@Composable
@Destination
fun PostScreen(
    navigator: DestinationsNavigator
) {

    BasicScaffold(
        topBar = {
            BasicTopBar(
                title = { Text(text = "새 선택지") },
                navigationIcon = {
                    ToolbarBackButton {
                        navigator.popBackStack()
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colors.onPrimary
    ) {
        PostContent()
    }
}

@Composable
private fun PostContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {

            UserProfile()

            Spacer(modifier = Modifier.height(25.dp))

            PostVoteContent()

            Spacer(modifier = Modifier.height(10.dp))

            CategoryToggleButton()

            Spacer(modifier = Modifier.height(25.dp))

            OptionalDropdownMenu()
        }

        Button(
            onClick = { /* TODO(post next button onClick) */ },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 18.dp)
                .padding(bottom = 42.dp),
            enabled = !(postTitle.value.isEmpty() || postOptionA.value.isEmpty() || postOptionB.value.isEmpty())
        ) {
            Text(text = "등록하기")
        }
    }
}

@Composable
private fun PostVoteContent(
    modifier: Modifier = Modifier
) {
    val currentScreenWidth = LocalConfiguration.current.screenWidthDp.dp - 35.dp
    val currentScreenWidthHalf = currentScreenWidth * 0.5f

    val backgroundColorOfA by remember { initialBackgroundColorOfA }
    val backgroundColorOfB by remember { initialBackgroundColorOfB }

    var title by remember { postTitle }
    var optionOfA by remember { postOptionA }
    var optionOfB by remember { postOptionB }

    var selectionOfA by remember { mutableStateOf(false) }
    var selectionOfB by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        Row(modifier = Modifier.padding(top = 50.dp)) {
            Box(modifier = Modifier
                .clip(
                    shape = if (!selectionOfA) {
                        RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                    } else {
                        RoundedCornerShape(20.dp)
                    }
                )
                .clickable { if (selectionOfA) selectionOfA = false else selectionOfA = true }
                .border(
                    1.dp, if (backgroundColorOfA == colorList[0]) {
                        MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
                    } else {
                        MaterialTheme.colors.onPrimary
                    },
                    shape =
                    if (!selectionOfA) {
                        RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                    } else {
                        RoundedCornerShape(20.dp)
                    }
                )
                .background(
                    if (backgroundColorOfA == colorList[colorList.lastIndex]) {
                        Brush.linearGradient(
                            colors = gradientColorList,
                            start = Offset.Zero,
                            end = Offset.Infinite
                        )
                    } else {
                        Brush.linearGradient(
                            colors = listOf(backgroundColorOfA, backgroundColorOfA),
                            start = Offset.Zero,
                            end = Offset.Infinite
                        )
                    }
                )
                .width(
                    if (selectionOfA) currentScreenWidth + 5.dp else {
                        if (selectionOfB) 0.dp else currentScreenWidthHalf
                    }
                )
                .height(currentScreenWidthHalf)

            ) {
                TextField(
                    value = optionOfA,
                    onValueChange = { optionOfA = it },
                    enabled = selectionOfA,
                    placeholder = {
                        Text(
                            text =
                            if (!selectionOfA) {
                                "A 선택지 글쓰기"
                            } else {
                                "A 선택지의 제목을 입력해주세요."
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = if (backgroundColorOfA == colorList[1]) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSurface
                        },
                        backgroundColor = colorTransparent,
                        cursorColor = MaterialTheme.colors.onSurface,
                        focusedIndicatorColor = colorTransparent,
                        unfocusedIndicatorColor = colorTransparent,
                        disabledIndicatorColor = colorTransparent,
                        disabledPlaceholderColor = if (backgroundColorOfA == colorList[1]) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSurface
                        },
                        placeholderColor = if (backgroundColorOfA == colorList[1]) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSurface
                        },
                        disabledTextColor = if (backgroundColorOfA == colorList[1]) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSurface
                        }
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )

                if (!selectionOfA) {
                    Button(
                        onClick = { },
                        enabled = false,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.BottomEnd)
                            .size(28.dp),
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 13.dp,
                            bottomStart = 20.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            disabledBackgroundColor = MaterialTheme.colors.onSurface.copy(
                                alpha = 0.2f
                            )
                        ),
                        border = BorderStroke(1.5.dp, MaterialTheme.colors.onPrimary),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "A",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                } else {
                    Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                        BackgroundColorSelectionButton(writingType = "A")
                    }
                }
            }

            Spacer(modifier = Modifier.width(if (selectionOfA || selectionOfB) 0.dp else 5.dp))

            Box(
                modifier = Modifier
                    .clip(
                        if (!selectionOfB) {
                            RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
                        } else {
                            RoundedCornerShape(20.dp)
                        }
                    )
                    .background(
                        if (backgroundColorOfB == colorList[colorList.lastIndex]) {
                            Brush.linearGradient(
                                colors = gradientColorList,
                                start = Offset.Zero,
                                end = Offset.Infinite
                            )
                        } else {
                            Brush.linearGradient(
                                colors = listOf(backgroundColorOfB, backgroundColorOfB),
                                start = Offset.Zero,
                                end = Offset.Infinite
                            )
                        }
                    )
                    .clickable { if (selectionOfB) selectionOfB = false else selectionOfB = true }
                    .border(
                        1.dp, if (backgroundColorOfB == colorList[0]) {
                            MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
                        } else {
                            MaterialTheme.colors.onPrimary
                        },
                        shape =
                        if (!selectionOfB) {
                            RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
                        } else {
                            RoundedCornerShape(20.dp)
                        }
                    )
                    .width(
                        if (selectionOfB) currentScreenWidth + 5.dp else {
                            if (selectionOfA) 0.dp else currentScreenWidthHalf
                        }
                    )
                    .height(currentScreenWidthHalf)
            ) {
                TextField(
                    value = optionOfB,
                    onValueChange = { optionOfB = it },
                    enabled = selectionOfB,
                    placeholder = {
                        Text(
                            text =
                            if (!selectionOfB) {
                                "B 선택지 글쓰기"
                            } else {
                                "B 선택지의 제목을 입력해주세요."
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1
                        )
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = if (backgroundColorOfB == colorList[1]) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSurface
                        },
                        backgroundColor = colorTransparent,
                        cursorColor = MaterialTheme.colors.onSurface,
                        focusedIndicatorColor = colorTransparent,
                        unfocusedIndicatorColor = colorTransparent,
                        disabledIndicatorColor = colorTransparent,
                        disabledPlaceholderColor = if (backgroundColorOfB == colorList[1]) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSurface
                        },
                        placeholderColor = if (backgroundColorOfB == colorList[1]) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSurface
                        },
                        disabledTextColor = if (backgroundColorOfB == colorList[1]) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSurface
                        }
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )

                if (!selectionOfB) {
                    Button(
                        onClick = { },
                        enabled = false,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.BottomStart)
                            .size(28.dp),
                        shape = RoundedCornerShape(
                            topStart = 13.dp,
                            topEnd = 20.dp,
                            bottomEnd = 20.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            disabledBackgroundColor = MaterialTheme.colors.onSurface.copy(
                                alpha = 0.2f
                            )
                        ),
                        border = BorderStroke(1.5.dp, MaterialTheme.colors.onPrimary),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "B",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                } else {
                    Box(modifier = Modifier.align(Alignment.BottomEnd)) {
                        BackgroundColorSelectionButton(writingType = "B")
                    }
                }
            }
        }

        val focusManager = LocalFocusManager.current

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomEnd = 10.dp,
                bottomStart = 10.dp
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.2f)),
            elevation = 4.dp
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                placeholder = {
                    Text(
                        text = "고민중인 이야기를 이곳에 적어보세요.",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1
                    )
                },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorTransparent,
                    unfocusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                    focusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                    cursorColor = MaterialTheme.colors.onSurface,
                ),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
        }
    }
}

@Composable
private fun CategoryToggleButton() {

    var selectedCategory by remember { selectedCategory }

    val onSelectionChange = { category: String ->
        selectedCategory = category
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "카테고리 분류하기")

        Icon(imageVector = Icons.Rounded.ExpandMore, contentDescription = null)

        Spacer(modifier = Modifier.height(10.dp))

        Surface(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 28.dp)
            ) {
                categoryState.forEach { category ->
                    val isSelected: Boolean = selectedCategory == category
                    Text(
                        text = category,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onSelectionChange(category) }
                            .background(
                                if (isSelected) {
                                    MaterialTheme.colors.primary
                                } else {
                                    MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
                                }
                            )
                            .padding(vertical = 4.dp, horizontal = 22.dp),
                        color = if (isSelected) {
                            MaterialTheme.colors.onPrimary
                        } else {
                            MaterialTheme.colors.onSurface
                        },
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OptionalDropdownMenu() {

    var isExpanded by remember { mutableStateOf(false) }

    var dropdownText by remember { dropdownMenu }

    val onDropdownSelectionChange = { text: String ->
        isExpanded = false
        dropdownText = text
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .clickable { isExpanded = true }
            .border(
                1.dp,
                MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = dropdownText)
        Icon(
            imageVector = Icons.Rounded.ExpandMore,
            contentDescription = null,
        )
    }

    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        offset = DpOffset(14.dp, 0.dp),
        expanded = isExpanded,
        onDismissRequest = { isExpanded = false }) {

        dropdowmMenuList.forEach { menu ->
            DropdownMenuItem(
                onClick = { onDropdownSelectionChange(menu) }
            ) {
                Text(text = menu)
            }
        }
    }
}

@Composable
private fun BackgroundColorSelectionButton(
    writingType: String
) {

    val colorListScrollState = rememberLazyListState()

    var selectedColorChip by remember {
        if (writingType == "A") initialBackgroundColorOfA
        else initialBackgroundColorOfB
    }

    val onChangedBackgroundColor = { color: Color ->
        selectedColorChip = color
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyRow(
            state = colorListScrollState,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {

            itemsIndexed(colorList) { index, color ->
                if (index == colorList.lastIndex) {
                    Surface(
                        color = colorTransparent,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = gradientColorList,
                                    start = Offset.Zero,
                                    end = Offset.Infinite
                                )
                            )
                            .clickable { onChangedBackgroundColor(color) }
                            .size(21.dp)
                            .border(2.dp, MaterialTheme.colors.onPrimary, shape = CircleShape)
                    ) { }
                } else {
                    Surface(
                        color = color as Color,
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clip(CircleShape)
                            .clickable { onChangedBackgroundColor(color) }
                            .size(21.dp)
                            .border(2.dp, MaterialTheme.colors.onPrimary, shape = CircleShape)
                    ) { }
                }
            }
        }


        Button(
            onClick = { },
            enabled = false,
            modifier = Modifier
                .padding(10.dp)
                .size(28.dp),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 13.dp,
                bottomStart = 20.dp
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.onPrimary),
            colors = ButtonDefaults.buttonColors(disabledBackgroundColor = MaterialTheme.colors.onPrimary),
            contentPadding = PaddingValues(0.dp)
        ) {
            if (selectedColorChip == colorTransparent) {
                Surface(
                    modifier = Modifier
                        .size(21.dp)
                        .background(
                            Brush.linearGradient(
                                colors = gradientColorList,
                                start = Offset.Zero,
                                end = Offset.Infinite
                            ),
                            CircleShape
                        ),
                    color = selectedColorChip
                ) { }
            } else {
                Surface(
                    modifier = Modifier.size(21.dp),
                    color = selectedColorChip,
                    shape = CircleShape
                ) { }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostVoteContentPreview() {
    MainTheme {
        PostVoteContent()
    }
}


@Composable
private fun UserProfile() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(top = 20.dp)
    ) {
        NetworkImage(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(width = 2.dp, color = Color(0xFF53654C)),
                    shape = CircleShape
                ),
            "https://picsum.photos/200"
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "헤르만 헤세",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "(Herman Hesse)",
                    fontSize = 7.sp,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            Text(
                text = "24분전",
                fontSize = 9.sp,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostContentPreview() {
    MainTheme {
        PostContent()
    }
}
