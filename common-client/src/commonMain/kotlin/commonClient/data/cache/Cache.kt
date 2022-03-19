package commonClient.data.cache

import kotlinx.coroutines.flow.Flow

interface Cache<T> {

    suspend fun save(value : T)

}