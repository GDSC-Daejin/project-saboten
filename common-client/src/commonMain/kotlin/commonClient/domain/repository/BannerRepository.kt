package commonClient.domain.repository

import commonClient.domain.entity.banner.Banner

interface BannerRepository {

    suspend fun getBanner() : List<Banner>

}