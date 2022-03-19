package commonClient.data.cache

interface Cache<T> {

    suspend fun save(value : T)

}