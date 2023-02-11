package commonClient.domain.entity

data class PagingRequest(
    val offset : Int? = null,
    val pageNumber : Int? = null,
    val pageSize : Int? = null,
)