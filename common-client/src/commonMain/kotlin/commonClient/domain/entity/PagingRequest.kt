package commonClient.domain.entity

data class PagingRequest(
    val offset : Long? = null,
    val pageNumber : Int? = null,
    val pageSize : Int? = null,
)