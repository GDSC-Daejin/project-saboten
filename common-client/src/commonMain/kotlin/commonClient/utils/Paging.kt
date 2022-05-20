package commonClient.utils

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

data class PagingWrapper<T>(
    val currentPage : Long,
    val isLastPage : Boolean,
    val content : List<T>
)

fun <V : Any> createPager(
    initialKey: Long = 0L,
    pagingConfig : PagingConfig = PagingConfig(
        pageSize = 30,
        enablePlaceholders = false
    ),
    call : suspend (Long) -> PagingWrapper<V>?
) = Pager(
    clientScope = CoroutineScope(Dispatchers.IO),
    config = pagingConfig,
    initialKey = initialKey,
    getItems = { nextPage, _ ->
        val apiResponse = call(nextPage)
        val currentKey = apiResponse?.currentPage ?: 0

        PagingResult(
            items = apiResponse?.content ?: emptyList(),
            currentKey = currentKey,
            nextKey = { if (apiResponse != null && !apiResponse.isLastPage) currentKey + 1 else null },
            prevKey = { if (apiResponse != null && currentKey != 0L) currentKey - 1 else null }
        )
    }
)