package common.model

import kotlinx.serialization.Serializable

@Serializable
class ApiResponse<T>(
    val error : String?,
    val data : T,
    val message : String?
)