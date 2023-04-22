package commonClient.data.repository

import commonClient.data.remote.endpoints.BannerApi
import commonClient.domain.entity.banner.Banner
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.BannerRepository
import org.koin.core.annotation.Single

@Single(binds = [BannerRepository::class])
class BannerRepositoryImp(private val bannerApi: BannerApi) : BannerRepository {

    override suspend fun getBanner(): List<Banner> {
        return bannerApi.getBanner().data?.map { it.toDomain() } ?: emptyList()
    }

}