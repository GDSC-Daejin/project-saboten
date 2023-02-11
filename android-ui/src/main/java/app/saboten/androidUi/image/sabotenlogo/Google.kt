package app.saboten.androidUi.image.sabotenlogo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SabotenIcons.Google: ImageVector
    get() {
        if (_google != null) return _google!!

        _google = Builder(name = "Google", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF4285F4)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(23.745f, 12.27f)
                curveTo(23.745f, 11.48f, 23.675f, 10.73f, 23.555f, 10.0f)
                lineTo(12.255f, 10.0f)
                lineTo(12.255f, 14.51f)
                lineTo(18.725f, 14.51f)
                curveTo(18.435f, 15.99f, 17.585f, 17.24f, 16.325f, 18.09f)
                lineTo(16.325f, 21.09f)
                lineTo(20.185f, 21.09f)
                curveTo(22.445f, 19.0f, 23.745f, 15.92f, 23.745f, 12.27f)
                close()
            }
            path(fill = SolidColor(Color(0xFF34A853)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(12.255f, 24.0f)
                curveTo(15.495f, 24.0f, 18.205f, 22.92f, 20.185f, 21.09f)
                lineTo(16.325f, 18.09f)
                curveTo(15.245f, 18.81f, 13.875f, 19.25f, 12.255f, 19.25f)
                curveTo(9.125f, 19.25f, 6.475f, 17.14f, 5.525f, 14.29f)
                lineTo(1.545f, 14.29f)
                lineTo(1.545f, 17.38f)
                curveTo(3.515f, 21.3f, 7.565f, 24.0f, 12.255f, 24.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFBBC05)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(5.525f, 14.29f)
                curveTo(5.275f, 13.57f, 5.145f, 12.8f, 5.145f, 12.0f)
                curveTo(5.145f, 11.2f, 5.285f, 10.43f, 5.525f, 9.71f)
                lineTo(5.525f, 6.62f)
                lineTo(1.545f, 6.62f)
                curveTo(0.725f, 8.24f, 0.255f, 10.06f, 0.255f, 12.0f)
                curveTo(0.255f, 13.94f, 0.725f, 15.76f, 1.545f, 17.38f)
                lineTo(5.525f, 14.29f)
                close()
            }
            path(fill = SolidColor(Color(0xFFEA4335)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero) {
                moveTo(12.255f, 4.75f)
                curveTo(14.025f, 4.75f, 15.605f, 5.36f, 16.855f, 6.55f)
                lineTo(20.275f, 3.13f)
                curveTo(18.205f, 1.19f, 15.495f, 0.0f, 12.255f, 0.0f)
                curveTo(7.565f, 0.0f, 3.515f, 2.7f, 1.545f, 6.62f)
                lineTo(5.525f, 9.71f)
                curveTo(6.475f, 6.86f, 9.125f, 4.75f, 12.255f, 4.75f)
                close()
            }
        }
            .build()
        return _google!!
    }

private var _google: ImageVector? = null