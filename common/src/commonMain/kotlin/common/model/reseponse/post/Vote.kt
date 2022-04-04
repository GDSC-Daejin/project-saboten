package common.model.reseponse.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vote(
    @SerialName("id") val id : Long,
    @SerialName("topic") val topic : String,
    @SerialName("count") val count : Int,
    @SerialName("color") val color : Colors
) {
    enum class Colors {
        WHITE,
        RED,
        GREEN,
        BLUE,
        YELLOW,
        PURPLE,
        PINK,
        ORANGE,
        BROWN,
        BLACK,
    }
}