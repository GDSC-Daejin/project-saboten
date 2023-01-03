package app.saboten.androidUi.image

import androidx.compose.ui.graphics.vector.ImageVector
import app.saboten.androidUi.image.sabotenIconPack.ABIcon
import kotlin.collections.List as ____KtList

object SabotenIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

val SabotenIconPack.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons =
            listOf(ABIcon)
        return __AllIcons!!
    }
