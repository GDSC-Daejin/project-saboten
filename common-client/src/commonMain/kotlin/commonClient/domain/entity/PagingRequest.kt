package commonClient.domain.entity

data class PagingRequest(
    val page : Long? = null,
    val size : Int? = null,
    val sort : List<String>? = null
)