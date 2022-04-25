package app.saboten.androidApp.ui.screens.post

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.QuestionAnswer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.styles.MainTheme

private val ROUNDED_CORNER_SHAPE = RoundedCornerShape(20.dp)
private val states = listOf(
    "성별",
    "연령별"
)

@Composable
fun PostVoterTurnoutScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.onPrimary)
        ) {
            Column() {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "전체 득표결과",
                    modifier = Modifier.padding(start = 20.dp),
                    style = MaterialTheme.typography.subtitle1
                )

                Spacer(modifier = Modifier.height(20.dp))

                VoterTurnoutTotalGraph()

                Spacer(modifier = Modifier.height(33.dp))
            }
        }

        Divider(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(24.dp))

        VoterTurnoutTabs()

        Spacer(modifier = Modifier.height(19.dp))

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 4.dp,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "favorite count",
                        tint = MaterialTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Text(text = "12")
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.QuestionAnswer,
                        contentDescription = "chat count",
                        tint = MaterialTheme.colors.primary
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Text(text = "12")
                }
            }
        }
    }
}


@Composable
fun VoterTurnoutTotalGraph() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(137.dp),
                strokeWidth = 5.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
                progress = 1f
            )
            CircularProgressIndicator(
                modifier = Modifier.size(137.dp),
                strokeWidth = 5.dp,
                color = Color(0xff97db6d),
                progress = 0.5f
            )
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                strokeWidth = 5.dp,
                progress = 1f,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
            )
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                strokeWidth = 5.dp,
                progress = 0.5f,
                color = Color(0xff62a54b)
            )
            Text(
                text = "A",
                style = MaterialTheme.typography.h3
            )
        }

        Spacer(modifier = Modifier.width(30.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(RectangleShape)
                        .background(Color(0xff97db6d))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(text = "A 선택지 주제 설명")
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(RectangleShape)
                        .background(Color(0xff62a54b))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(text = "B 선택지 주제 설명")
            }

            Spacer(modifier = Modifier.height(35.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = "총 참여자 수")

                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = "867명",
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}

@Composable
private fun VoterTurnoutTabs() {

    var selectedOption by remember {
        mutableStateOf(states[0])
    }

    val onSelectionChange = { tab: String ->
        selectedOption = tab
    }

    Surface(
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp,
        modifier = Modifier.wrapContentWidth()
    ) {
        Row(
            modifier = Modifier
                .clip(shape = ROUNDED_CORNER_SHAPE)
                .background(MaterialTheme.colors.onPrimary)
        ) {
            states.forEach { tab ->
                val isSelected = tab == selectedOption
                Text(
                    text = tab,
                    style = if (isSelected) MaterialTheme.typography.subtitle1 else MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Light
                    ),
                    color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface.copy(
                        alpha = 0.6f
                    ),
                    modifier = Modifier
                        .clip(shape = ROUNDED_CORNER_SHAPE)
                        .clickable { onSelectionChange(tab) }
                        .background(
                            if (isSelected) Color(0xFF57B04B) else MaterialTheme.colors.onPrimary
                        )
                        .padding(
                            vertical = 10.dp,
                            horizontal = 22.dp
                        )
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    TabClickedChange(selectedTab = selectedOption)
}

@Composable
private fun TabClickedChange(
    selectedTab: String
) {
    if (selectedTab == "성별") {
        VoterTurnoutGraphGenderTab()
    } else {
        VoterTurnoutGraphAgeTab()
    }
}

@Composable
private fun VoterTurnoutGraphAgeTab() {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 34.dp,
                top = 36.dp,
                end = 41.dp,
                bottom = 33.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VoterTurnoutGraphAgeTabContent(10)

            Spacer(modifier = Modifier.height(22.dp))

            VoterTurnoutGraphAgeTabContent(20)

            Spacer(modifier = Modifier.height(22.dp))

            VoterTurnoutGraphAgeTabContent(30)

            Spacer(modifier = Modifier.height(22.dp))

            VoterTurnoutGraphAgeTabContent(40)
        }
    }
}

@Composable
private fun VoterTurnoutGraphAgeTabContent(
    age: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${age}대",
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.width(20.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .height(8.dp)
                .clip(ROUNDED_CORNER_SHAPE)
                .fillMaxWidth(),
            progress = 0.5f,
            color = Color(0xFF57B04B),
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
        )
    }
}

@Composable
private fun VoterTurnoutGraphGenderTab() {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            VoterTurnoutGraphGenderTabContent(gender = "여성")

            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFD0D0D0)
            )

            VoterTurnoutGraphGenderTabContent(gender = "남성")
        }
    }
}

@Composable
private fun VoterTurnoutGraphGenderTabContent(
    gender: String,
    selectedCount: Int = 0
) {
    Column(
        modifier = Modifier.padding(
            horizontal = 35.dp,
            vertical = 20.dp
        )
    ) {
        Text(
            text = gender,
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(22.dp))

        LinearProgressIndicator(
            modifier = Modifier
                .height(8.dp)
                .clip(ROUNDED_CORNER_SHAPE)
                .fillMaxWidth(),
            progress = 0.5f,
            color = Color(0xFF57B04B),
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
        )

        Spacer(modifier = Modifier.height(7.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "A 득표수",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "B 득표수",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostVoterTurnoutScreenPreview() {
    MainTheme {
        PostVoterTurnoutScreen()
    }
}