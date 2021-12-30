package styles

import csstype.Border
import csstype.Length
import csstype.LineStyle

val dividerBoarder = Border("1px".unsafeCast<Length>(), LineStyle.solid, Colors.lightGray).toString()