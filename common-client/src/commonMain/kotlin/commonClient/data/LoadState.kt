package commonClient.data

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

typealias EmptyLoadState = LoadState<Unit>

@OptIn(ExperimentalContracts::class)
fun <T> LoadState<T>.isLoading(): Boolean {
    contract {
        returns(true) implies (this@isLoading is LoadState.Loading)
    }
    return this is LoadState.Loading
}

@OptIn(ExperimentalContracts::class)
fun <T> LoadState<T>.isFailed(): Boolean {
    contract {
        returns(true) implies (this@isFailed is LoadState.Failed)
    }
    return this is LoadState.Failed
}

sealed interface LoadState<T> {

//    val isLoading: Boolean get() = this is Loading
//
//    val isFailed: Boolean get() = this is Failed

    fun getDataOrNull(): T? = when (this) {
        is Loading -> null
        is Success -> data
        is Failed -> null
    }

    data class Success<T>(val data: T) : LoadState<T>
    class Loading<T> : LoadState<T>
    class Failed<T>(val throwable: Throwable, val cachedData: T? = null) : LoadState<T>

    companion object {

        fun <T> success(data: T) = Success(data)
        fun <T> loading() = Loading<T>()
        fun <T> failed(throwable: Throwable, data: T? = null) = Failed(throwable, data)

    }

}

fun <T, R> LoadState<T>.map(mapper: (T) -> R): LoadState<R> {
    return when (this) {
        is LoadState.Failed -> LoadState.Failed(throwable, cachedData?.let { mapper(it) })
        is LoadState.Loading -> LoadState.Loading()
        is LoadState.Success -> LoadState.Success(mapper(data))
    }
}
