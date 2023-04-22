package common.model.reseponse.paging

import kotlinx.serialization.Serializable

@Serializable
data class Pageable(
    val offset: Long,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: SortX,
    val unpaged: Boolean
)