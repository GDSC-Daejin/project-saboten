package commonClient.domain.usecase.post

import commonClient.data.cache.SearchHistoryCache
import org.koin.core.annotation.Single

@Single
class ClearRecentSearchTextsUseCase(
    private val searchHistoryCache: SearchHistoryCache,
) {

    suspend operator fun invoke() = searchHistoryCache.clear()

}