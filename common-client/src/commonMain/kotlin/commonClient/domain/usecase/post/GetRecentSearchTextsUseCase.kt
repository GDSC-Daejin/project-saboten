package commonClient.domain.usecase.post

import commonClient.data.cache.SearchHistoryCache
import org.koin.core.annotation.Single

@Single
class GetRecentSearchTextsUseCase(
    private val searchHistoryCache: SearchHistoryCache,
) {

    operator fun invoke() = searchHistoryCache.data

}