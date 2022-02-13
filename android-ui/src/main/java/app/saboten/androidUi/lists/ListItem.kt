package app.saboten.androidUi.lists

import android.text.style.BackgroundColorSpan
import app.saboten.androidUi.utils.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout


@Composable
fun BasicListItem(
    modifier: Modifier = Modifier,
    backgroundColor : Color = MaterialTheme.colors.surface,
    title: String,
    subtitle: String? = null,
    leftSideUi: (@Composable () -> Unit)? = null,
    rightSideUi: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = backgroundColor,
        onClick = onClick
    ) {

        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            val (leftSideUiRef, textContainer, rightSideUiRef) = createRefs()

            if (leftSideUi != null) {
                Row(
                    modifier = Modifier.constrainAs(
                        leftSideUiRef,
                        t2t(textContainer) + s2s() + b2b(textContainer)
                    )
                ) {
                    leftSideUi()
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .run {
                        if (leftSideUi == null && rightSideUi == null) fillMaxWidth()
                        else {
                            constrainAs(
                                textContainer,
                                t2t() + b2b() +
                                        if (leftSideUi != null) {
                                            s2e(leftSideUiRef) + fillWidthToConstraint
                                        } else {
                                            s2s()
                                        } +
                                        if (rightSideUi != null) {
                                            e2s(rightSideUiRef) + fillWidthToConstraint
                                        } else {
                                            e2e()
                                        }

                            )
                        }
                    },
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle2
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            if (rightSideUi != null) {
                Row(
                    modifier = Modifier.constrainAs(
                        rightSideUiRef,
                        t2t(textContainer) + e2e() + b2b(textContainer)
                    )
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    rightSideUi()
                }
            }

        }

    }

}