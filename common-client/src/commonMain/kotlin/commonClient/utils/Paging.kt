package commonClient.utils

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.dispatcher
import common.model.reseponse.PagingData
import commonClient.presentation.PlatformViewModel
import kotlinx.coroutines.CoroutineScope

fun <T, R> PagingData<T>.map(mapper: (T) -> R): PagingData<R> {
    return PagingData(
        data.map(mapper),
        nextKey, count
    )
}

fun <K : Any, T : Any> PlatformViewModel.createPager(
    pageSize: Int,
    initialKey: K,
    getItems: suspend (K, Int) -> PagingResult<K, T>
) = Pager(
    platformViewModelScope,
    PagingConfig(pageSize),
    initialKey,
    getItems
)