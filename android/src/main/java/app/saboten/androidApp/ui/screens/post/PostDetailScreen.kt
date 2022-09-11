package app.saboten.androidApp.ui.screens.post

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidApp.ui.list.CommentItem
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.MainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.domain.entity.post.Post
import commonClient.domain.entity.user.User

@Composable
@Destination
fun PostDetailScreen(
    post: Post,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    BasicScaffold(
        topBar = {
            BasicTopBar(
                title = {
                    Text("PostDetail")
                },
                navigationIcon = {
                    ToolbarBackButton {
                        navigator.popBackStack()
                    }
                }
            )
        },
        bottomBar = {
            BottomCommentContent(onSavedClick = {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            })
        }
    ) { innerPadding ->
        PostDetailContent(post,
            modifier = Modifier.padding(innerPadding),
            onFavoriteClicked = {},
            onMoreClicked = {},
            onVoteClicked = {},
            onRefreshClicked = {},
            onDetailClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    MainTheme {
//        PostDetailContent(
//            Post()
//        )
    }
}

@Composable
private fun PostDetailContent(
    post: Post,
    modifier: Modifier = Modifier,
    onFavoriteClicked: () -> Unit,
    onMoreClicked: () -> Unit,
    onVoteClicked: (String) -> Unit,
    onRefreshClicked: () -> Unit,
    onDetailClicked: () -> Unit
) {

    val viewScroll = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(viewScroll)
        ) {
            PostDetailProfile(
                post.author,
                onFavoriteClicked = onFavoriteClicked,
                onMoreClicked = onMoreClicked
            )

            Spacer(modifier = Modifier.height(25.dp))

            PostDetailVoteContent(onVoteClicked = onVoteClicked)

            Spacer(modifier = Modifier.height(23.dp))

            PostDetailVoteIndicator()

            Spacer(modifier = Modifier.height(34.dp))

            ViewDivider()

            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onDetailClicked() },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.BarChart,
                        contentDescription = "bar chart"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "자세한 득표 결과보기",
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            ViewDivider()

            CommentList(onRefreshClicked = onRefreshClicked)
        }
    }
}

@Composable
private fun ViewDivider() {
    Divider(
        thickness = 0.5.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
    )
}


@Composable
private fun PostDetailProfile(
    author: User,
    onFavoriteClicked: () -> Unit,
    onMoreClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImage(
            modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(width = 2.dp, color = Color(0xFF53654C)),
                    shape = CircleShape
                ),
            "https://picsum.photos/200"
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {

            Text(
                text = author.nickname,
                style = MaterialTheme.typography.caption
            )

            Text(
                text = "24분전",
                fontSize = 9.sp,
                modifier = Modifier.padding(top = 5.dp)
            )
        }

    }
}

@Composable
private fun PostDetailVoteContent(
    modifier: Modifier = Modifier,
    onVoteClicked: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(top = 50.dp)) {
            Box {
                Button(
                    shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFA66FEA)),
                    modifier = Modifier
                        .width(screenWidth * 0.5f)
                        .height(screenWidth * 0.3f)
                        .padding(start = 15.dp),
                    onClick = { onVoteClicked("A") }
                ) {
                    Text(
                        text = "파인애플 피자",
                        color = MaterialTheme.colors.onPrimary
                    )
                }

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
                    colors = ButtonDefaults.buttonColors(disabledBackgroundColor = MaterialTheme.colors.primary),
                    border = BorderStroke(1.5.dp, Color.White),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "A",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(5.dp))

            Box {
                Button(
                    shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF859DFC)),
                    modifier = Modifier
                        .width(screenWidth * 0.5f)
                        .height(screenWidth * 0.3f)
                        .padding(end = 15.dp),
                    onClick = { onVoteClicked("B") }
                ) {
                    Text(
                        text = "민트 초코 아이스크림",
                        color = MaterialTheme.colors.onPrimary
                    )
                }

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
                            alpha = 0.3f
                        )
                    ),
                    border = BorderStroke(1.5.dp, Color.White),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "B",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 15.dp),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
                bottomEnd = 10.dp,
                bottomStart = 10.dp
            ),
            border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.2f)),
            elevation = 4.dp
        ) {
            Text(
                text = "무인도에 떨어졌는데 음식 둘 중 하나만 먹어야 된다면?",
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
private fun PostDetailVoteIndicator(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "55%",
            style = MaterialTheme.typography.body2
        )

        Spacer(modifier = Modifier.width(15.dp))

        LinearProgressIndicator(
            modifier = Modifier
                .height(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.CenterVertically),
            progress = 0.55f,
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = "45%",
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun CommentList(
    count: Int = 0,
    onRefreshClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.05f))
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "참여자($count)",
            )

            IconButton(
                onClick = { onRefreshClicked() },
                modifier = Modifier.then(Modifier.size(24.dp))
            ) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = "post detail refresh button"
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            (0..5).forEach {

                if (it % 2 == 0) {
                    CommentItem(
                        type = false,
                        text = "와 이걸 고민해? 당연히 전자지 와 이걸 고민해? 당연히 전자지 와 이걸 고민해? 당연히 전자지 와 이걸 고민해? 당연히 전자지 와 이걸 고민해? 당연히 전자지"
                    )
                } else {
                    CommentItem(
                        type = true,
                        text = "와 이걸 고민해? 당연히 전자지 와 이걸 고민해? 당연히 전자지 와 이걸 고민해? 당연히 전자지 와 이걸 고민해? 당연히 전자지 와 이걸 고민해? 당연히 전자지"
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
            }

            (0..5).forEach {

                if (it % 2 == 0) {
                    CommentItem(
                        type = false,
                        text = "짧은 댓글"
                    )
                } else {
                    CommentItem(
                        type = true,
                        text = "짧은 댓글"
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

@Composable
private fun BottomCommentContent(
    modifier: Modifier = Modifier,
    text: String = "",
    onSavedClick: (String) -> Unit
) {

    var commentText by remember { mutableStateOf(text) }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.onPrimary)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(10.dp))

        NetworkImage(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape),
            "https://picsum.photos/200"
        )

        Spacer(modifier = Modifier.width(10.dp))

        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp),
            value = commentText,
            onValueChange = { commentText = it },
            shape = RoundedCornerShape(7.dp),
            placeholder = { Text(text = "선인장 집사(으)로 댓글 달기", fontWeight = FontWeight.Light) },
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )

        Spacer(modifier = Modifier.width(10.dp))

        IconButton(
            onClick = {
                focusManager.clearFocus()
                onSavedClick(commentText)
                commentText = ""
            },
            modifier = Modifier.background(
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                CircleShape
            )
        ) {
            Icon(imageVector = Icons.Rounded.Send, contentDescription = "create comment")
        }

        Spacer(modifier = Modifier.width(10.dp))
    }
}