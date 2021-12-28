package app.saboten.common.entities

class ApiResponse<T>(
    val error : String?,
    val data : T,
    val message : String?
)