package app.saboten.androidUi.image // ktlint-disable filename

import androidx.compose.ui.graphics.vector.ImageVector
import app.saboten.androidUi.image.sabotenIconPack.ABIcon
import app.saboten.androidUi.image.sabotenIconPack.CommentIcon
import app.saboten.androidUi.image.sabotenIconPack.HeartIcon
import app.saboten.androidUi.image.sabotenIconPack.SabotenLogo
import app.saboten.androidUi.image.sabotenIconPack.ScrapIcon
import kotlin.collections.List as ____KtList

object SabotenIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

val SabotenIconPack.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons =
            listOf(HeartIcon, ScrapIcon, ABIcon, CommentIcon, SabotenLogo)
        return __AllIcons!!
    }
