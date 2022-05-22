package commonClient.utils

import kotlinx.coroutines.Dispatchers

actual val Dispatchers.IO get() = Dispatchers.IO