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

val SabotenIconPack.ScrapIcon: ImageVector
    get() {
        if (_scrapIcon != null) {
            return _scrapIcon!!
        }
        _scrapIcon = Builder(
            name = "ScrapIcon",
            defaultWidth = 28.0.dp,
            defaultHeight =
            36.0.dp,
            viewportWidth = 28.0f,
            viewportHeight = 36.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFE368)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(24.0f, 0.0f)
                horizontalLineTo(4.0f)
                curveTo(1.8f, 0.0f, 0.0f, 1.8f, 0.0f, 4.0f)
                verticalLineTo(36.0f)
                lineTo(14.0f, 30.0f)
                lineTo(28.0f, 36.0f)
                verticalLineTo(4.0f)
                curveTo(28.0f, 1.8f, 26.2f, 0.0f, 24.0f, 0.0f)
                close()
            }
        }
            .build()
        return _scrapIcon!!
    }

private var _scrapIcon: ImageVector? = null
