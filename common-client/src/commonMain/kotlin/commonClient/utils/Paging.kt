package commonClient.utils

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingResult
import common.model.reseponse.PagingResponse
import common.model.reseponse.paging.NewPagingResponse
import commonClient.presentation.PlatformViewModel

fun <T, R> NewPagingResponse<T>.map(mapper: (T) -> R): NewPagingResponse<R> {
    return NewPagingResponse(
        content.map(mapper),
        empty,
        first,
        last,
        number,
        numberOfElements,
        pageable,
        size,
        sort,
        totalElements,
        totalPages
    )
}

fun <T, R> PagingResponse<T>.map(mapper: (T) -> R): PagingResponse<R> {
    return PagingResponse(
        data.map(mapper),
        nextKey, count
    )
}

fun <K : Any, T : Any> PlatformViewModel<*, *>.createPager(
    pageSize: Int,
    initialKey: K?,
    getItems: suspend (K?, Int) -> PagingResult<K, T>
) = Pager(
    platformViewModelScope,
    PagingConfig(pageSize),
    initialKey,
    getItems
)