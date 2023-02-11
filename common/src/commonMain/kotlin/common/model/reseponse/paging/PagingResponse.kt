package common.model.reseponse.paging

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse<T>(
    @SerialName("data") val data: List<T>,
    @SerialName("next_key") val nextKey : Long? = null,
    @SerialName("count") val count : Long? = null,
)