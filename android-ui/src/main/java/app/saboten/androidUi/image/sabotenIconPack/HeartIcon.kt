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

val SabotenIconPack.HeartIcon: ImageVector
    get() {
        if (_heartIcon != null) {
            return _heartIcon!!
        }
        _heartIcon = Builder(
            name = "HeartIcon",
            defaultWidth = 23.0.dp,
            defaultHeight = 20.0.dp,
            viewportWidth = 23.0f,
            viewportHeight = 20.0f
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
                moveTo(16.6024f, 0.4667f)
                curveTo(14.5064f, 0.4667f, 12.7154f, 1.9607f, 11.7314f, 2.9907f)
                curveTo(10.7474f, 1.9607f, 8.9604f, 0.4667f, 6.8654f, 0.4667f)
                curveTo(3.2544f, 0.4667f, 0.7334f, 2.9837f, 0.7334f, 6.5867f)
                curveTo(0.7334f, 10.5567f, 3.8644f, 13.1227f, 6.8934f, 15.6047f)
                curveTo(8.3234f, 16.7777f, 9.8034f, 17.9897f, 10.9384f, 19.3337f)
                curveTo(11.1294f, 19.5587f, 11.4094f, 19.6887f, 11.7034f, 19.6887f)
                horizontalLineTo(11.7614f)
                curveTo(12.0564f, 19.6887f, 12.3354f, 19.5577f, 12.5254f, 19.3337f)
                curveTo(13.6624f, 17.9897f, 15.1414f, 16.7767f, 16.5724f, 15.6047f)
                curveTo(19.6004f, 13.1237f, 22.7334f, 10.5577f, 22.7334f, 6.5867f)
                curveTo(22.7334f, 2.9837f, 20.2124f, 0.4667f, 16.6024f, 0.4667f)
                close()
            }
        }
            .build()
        return _heartIcon!!
    }

private var _heartIcon: ImageVector? = null
