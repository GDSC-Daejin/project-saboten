package commonClient.domain.usecase.post

import commonClient.data.cache.SearchHistoryCache
import org.koin.core.annotation.Single

@Single
class RemoveRecentSearchTextsUseCase(
    private val searchHistoryCache: SearchHistoryCache,
) {

    suspend operator fun invoke(history: String) = searchHistoryCache.remove(history)

}