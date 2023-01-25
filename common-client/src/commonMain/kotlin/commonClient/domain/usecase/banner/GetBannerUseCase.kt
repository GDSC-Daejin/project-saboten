package commonClient.domain.usecase.banner

import commonClient.domain.repository.BannerRepository
import org.koin.core.annotation.Single

@Single
class GetBannerUseCase(private val bannerRepository: BannerRepository) {

    suspend operator fun invoke() = bannerRepository.getBanner()

}