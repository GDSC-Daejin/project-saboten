package styles

import csstype.Border
import csstype.Color
import csstype.Length
import csstype.LineStyle

val dividerBoarder = Border(
    "1px".unsafeCast<Length>(), LineStyle.solid,
    Color(Colors.lightGray.value)
).toString()