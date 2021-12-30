package styles

import kotlinx.css.CssBuilder
import kotlinx.css.LinearDimension
import kotlinx.css.px

/*
@media (min-width:320px)  { *//* smartphones, iPhone, portrait 480x320 phones *//* }
@media (min-width:481px)  { *//* portrait e-readers (Nook/Kindle), smaller tablets @ 600 or @ 640 wide. *//* }
@media (min-width:641px)  { *//* portrait tablets, portrait iPad, landscape e-readers, landscape 800x480 or 854x480 phones *//* }
@media (min-width:961px)  { *//* tablet, landscape iPad, lo-res laptops ands desktops *//* }
@media (min-width:1025px) { *//* big landscape tablets, laptops, and desktops *//* }
@media (min-width:1281px) { *//* hi-res laptops and desktops *//* }
*/
val MAX_WIDTH_SMALL_PHONE = 320.px
val MAX_WIDTH_PORTRAIT_E_READERS = 481.px
val MAX_WIDTH_PORTRAIT_TABLETS = 641.px
val MAX_WIDTH_LANDSCAPE_TABLETS = 961.px
val MAX_WIDTH_BIG_TABLETS = 1025.px
val MAX_WIDTH_DESKTOP = 1281.px

fun CssBuilder.mediaMaxWidth(
    vararg size: LinearDimension,
    css: CssBuilder.() -> Unit
) {
    size.forEach {
        media("(max-width: $it)", css)
    }
}