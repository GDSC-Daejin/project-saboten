package app.saboten.androidApp.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.DragBar
import app.saboten.androidUi.styles.SabotenColors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import java.io.Serializable

sealed interface PostSettingDialogResult: Serializable {
    object Modify : PostSettingDialogResult
    object Delete : PostSettingDialogResult
}

@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun PostSettingDialog(
    resultNavigator: ResultBackNavigator<PostSettingDialogResult>,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFAFA))
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DragBar()

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = { resultNavigator.navigateBack(PostSettingDialogResult.Modify) }) {
            Text(text = "수정하기", color = SabotenColors.grey600,
                style = MaterialTheme.typography.body1)
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = { resultNavigator.navigateBack(PostSettingDialogResult.Delete) }) {
            Text(text = "삭제하기", color = SabotenColors.grey600,
                style = MaterialTheme.typography.body1)
        }

    }
}