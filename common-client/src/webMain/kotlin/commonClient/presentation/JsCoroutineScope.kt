package commonClient.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val jsCoroutineScope get() = CoroutineScope(Dispatchers.Default)