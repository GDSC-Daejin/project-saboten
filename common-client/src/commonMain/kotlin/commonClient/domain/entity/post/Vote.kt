package commonClient.domain.entity.post

data class Vote(
    val id : Long,
    val topic : String,
    val count : Int,
    val color : VoteColors
)