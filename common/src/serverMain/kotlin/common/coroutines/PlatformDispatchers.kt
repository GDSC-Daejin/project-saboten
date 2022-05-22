package common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object PlatformDispatchers {
    actual val IO: CoroutineDispatcher = Dispatchers.IO
}