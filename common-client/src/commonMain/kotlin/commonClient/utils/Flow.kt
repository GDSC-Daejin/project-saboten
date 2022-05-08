package commonClient.utils

import commonClient.data.LoadState
import kotlinx.coroutines.flow.*


fun <T> Flow<T>.toLoadState(default: T? = null): Flow<LoadState<T>> =
    map { LoadState.success(it) as LoadState<T> }
        .onStart { emit(LoadState.loading()) }
        .catch { cause -> emit(LoadState.failed(cause, default)) }

fun <T> Flow<LoadState<T>>.onlyAtSuccess(): Flow<LoadState.Success<T>> =
    filter { it is LoadState.Success }.map { it as LoadState.Success<T> }