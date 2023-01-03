package app.saboten.androidUi.image.sabotenIconPack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.image.SabotenIconPack

val SabotenIconPack.CommentIcon: ImageVector
    get() {
        if (_commentIcon != null) {
            return _commentIcon!!
        }
        _commentIcon = Builder(
            name = "CommentIcon",
            defaultWidth = 22.0.dp,
            defaultHeight =
            22.0.dp,
            viewportWidth = 22.0f,
            viewportHeight = 22.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFC6C6C6)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(19.667f, 4.5f)
                horizontalLineTo(18.5837f)
                verticalLineTo(13.1666f)
                curveTo(18.5837f, 13.7625f, 18.0962f, 14.25f, 17.5003f, 14.25f)
                horizontalLineTo(4.5003f)
                verticalLineTo(15.3333f)
                curveTo(4.5003f, 16.525f, 5.4753f, 17.5f, 6.667f, 17.5f)
                horizontalLineTo(17.5003f)
                lineTo(21.8337f, 21.8333f)
                verticalLineTo(6.6666f)
                curveTo(21.8337f, 5.475f, 20.8587f, 4.5f, 19.667f, 4.5f)
                close()
                moveTo(16.417f, 9.9166f)
                verticalLineTo(2.3333f)
                curveTo(16.417f, 1.1416f, 15.442f, 0.1666f, 14.2503f, 0.1666f)
                horizontalLineTo(2.3337f)
                curveTo(1.142f, 0.1666f, 0.167f, 1.1416f, 0.167f, 2.3333f)
                verticalLineTo(16.4166f)
                lineTo(4.5003f, 12.0833f)
                horizontalLineTo(14.2503f)
                curveTo(15.442f, 12.0833f, 16.417f, 11.1083f, 16.417f, 9.9166f)
                close()
            }
        }
            .build()
        return _commentIcon!!
    }

private var _commentIcon: ImageVector? = null
