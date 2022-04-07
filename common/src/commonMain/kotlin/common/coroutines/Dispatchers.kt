package common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

expect object PlatformDispatchers {
    val IO : CoroutineDispatcher
}