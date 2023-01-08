package commonClient.data

import common.model.reseponse.ApiResponse
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

@OptIn(ExperimentalContracts::class)
fun <T> LoadState<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is LoadState.Success)
    }
    return this is LoadState.Success
}


sealed interface LoadState<T> {

    fun getDataOrNull(): T? = when (this) {
        is Idle -> null
        is Loading -> null
        is Success -> data
        is Failed -> null
    }

    class Idle<T> : LoadState<T>

    data class Success<T>(val data: T) : LoadState<T>
    class Loading<T> : LoadState<T>
    class Failed<T>(val throwable: Throwable, val cachedData: T? = null) : LoadState<T>

    companion object {

        fun <T> success(data: T) = Success(data)
        fun <T> loading() = Loading<T>()

        fun <T> idle() = Idle<T>()

        fun <T> failed(throwable: Throwable, data: T? = null) = Failed(throwable, data)

    }

}

fun <T, R> LoadState<T>.map(mapper: (T) -> R): LoadState<R> {
    return when (this) {
        is LoadState.Failed -> LoadState.Failed(throwable, cachedData?.let { mapper(it) })
        is LoadState.Loading -> LoadState.Loading()
        is LoadState.Idle -> LoadState.Idle()
        is LoadState.Success -> LoadState.Success(mapper(data))
    }
}
