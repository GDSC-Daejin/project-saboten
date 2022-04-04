package common.model.request.post.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteCreateRequest(
    @SerialName("topic") val topic : String,
    @SerialName("color") val color : Colors
) {
    enum class Colors(val value : Int) {
        WHITE(0),
        RED(1),
        GREEN(2),
        BLUE(3),
        YELLOW(4),
        PURPLE(5),
        PINK(6),
        ORANGE(7),
        BROWN(8),
        BLACK(9),
    }
}