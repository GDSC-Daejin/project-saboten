package app.saboten.androidApp.ui.screens.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.MainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun SignupSelectionScreen(
    navigator: DestinationsNavigator
) {
    BasicScaffold(
        topBar = {
            BasicTopBar(
                title = { /* TODO("signup selection top bar title") */ },
                navigationIcon = {
                    ToolbarBackButton {
                        navigator.popBackStack()
                    }
                }
            )
        },
        backgroundColor = MaterialTheme.colors.onPrimary
    ) {
        SignupSelectionContent()
    }
}

@Composable
private fun SignupSelectionContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "성별, 연령대를 선택해주세요.",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "선택하신 정보를 기반으로 통계가 표시됩니다.",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body2,
            )

            Spacer(modifier = Modifier.height(37.dp))

            SignupGenderToggleButton()

            Spacer(modifier = Modifier.height(45.dp))

            SignupAgeToggleButton()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /* TODO("signup selection next button") */ },
                modifier = Modifier
                    .height(40.dp)
                    .width(163.dp)
            ) {
                Text(text = "다음")
            }

            Spacer(modifier = Modifier.height(11.dp))

            Text(
                text = "건너뛰기",
                modifier = Modifier.clickable {
                    /* TODO("signup selection jump button") */
                },
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                style = MaterialTheme.typography.caption
            )

            Spacer(modifier = Modifier.height(47.dp))

            Surface(
                modifier = Modifier.size(width = 163.dp, height = 5.dp),
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
            ) {}

            Spacer(modifier = Modifier.height(17.dp))
        }
    }
}


@Composable
private fun SignupGenderToggleButton() {

    val genderState = listOf(
        "여성",
        "남성"
    )

    var genderSelection by remember {
        mutableStateOf(genderState[0])
    }

    val onGenderSelectionChange = { text: String ->
        genderSelection = text
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        genderState.forEach { gender ->
            val isGenderSelect = gender == genderSelection

            Box(modifier = Modifier
                .padding(horizontal = 4.dp)
                .clip(CircleShape)
                .clickable { onGenderSelectionChange(gender) }
            ) {
                Surface(
                    modifier = Modifier.size(120.dp),
                    color = if (isGenderSelect) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
                    }
                ) { }

                Text(
                    text = gender,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 15.dp),
                    color = if (isGenderSelect) {
                        MaterialTheme.colors.onPrimary
                    } else {
                        MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    }
                )
            }
        }
    }
}

@Composable
private fun SignupAgeToggleButton() {

    val ageStateTop = listOf(
        "10대", "20대", "30대"
    )

    val ageStateBottom = listOf(
        "40대", "50대", "기 타"
    )

    var ageSelection by remember {
        mutableStateOf(ageStateTop[0])
    }

    val onAgeSelectionChange = { text: String ->
        ageSelection = text
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            ageStateTop.forEach { age ->
                val isAgeSelect = age == ageSelection
                SignupAgeToggleButtonComponent(
                    isAgeSelect = isAgeSelect,
                    state = age,
                    onAgeSelectionChange = onAgeSelectionChange
                )
            }
        }

        Spacer(modifier = Modifier.height(11.dp))

        Row {
            ageStateBottom.forEach { age ->
                val isAgeSelect = age == ageSelection
                SignupAgeToggleButtonComponent(
                    isAgeSelect = isAgeSelect,
                    state = age,
                    onAgeSelectionChange = onAgeSelectionChange
                )
            }
        }
    }
}

@Composable
private fun SignupAgeToggleButtonComponent(
    isAgeSelect: Boolean,
    state: String,
    onAgeSelectionChange: (String) -> Unit
) {
    Button(
        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
        modifier = Modifier.padding(horizontal = 7.5.dp),
        onClick = { onAgeSelectionChange(state) },
        colors = if (isAgeSelect) {
            ButtonDefaults.buttonColors(MaterialTheme.colors.primary)
        } else {
            ButtonDefaults.buttonColors(MaterialTheme.colors.onPrimary)
        },
        border = if (isAgeSelect) {
            BorderStroke(1.dp, MaterialTheme.colors.primary)
        } else {
            BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
        },
        contentPadding = PaddingValues(start = 21.dp, top = 28.dp, end = 22.dp, bottom = 27.dp)
    ) {
        Text(
            text = state,
            color = if (isAgeSelect) {
                MaterialTheme.colors.onPrimary
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignupSelectionContentPreview() {
    MainTheme {
        SignupSelectionContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun SignupAgeToggleButtonPreview() {
    MainTheme {
        SignupAgeToggleButton()
    }
}

@Preview(showBackground = true)
@Composable
private fun SignupGenderToggleButtonPreview() {
    MainTheme {
        SignupGenderToggleButton()
    }
}