package commonClient.domain.mapper

import common.model.reseponse.banner.BannerResponse
import commonClient.domain.entity.banner.Banner

fun BannerResponse.toDomain() = Banner(
    id = id,
    title = title,
    subtitle = subtitle,
    category = category,
    startColor = startColor,
    endColor = endColor,
    iconUrl = iconUrl,
)