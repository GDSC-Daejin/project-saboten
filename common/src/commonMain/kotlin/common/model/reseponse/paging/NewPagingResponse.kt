package common.model.reseponse.paging

import kotlinx.serialization.Serializable

@Serializable
data class NewPagingResponse<T>(
    val content: List<T>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)