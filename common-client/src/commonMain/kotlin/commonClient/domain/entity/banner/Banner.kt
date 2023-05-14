package commonClient.domain.entity.banner

data class Banner(
    val id: Long,
    val title: String,
    val subtitle: String,
    val category: String,
    val startColor: String,
    val endColor: String,
    val iconUrl: String,
)