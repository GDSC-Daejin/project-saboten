package common.model

class ApiResponse<T>(
    val error : String?,
    val data : T,
    val message : String?
)