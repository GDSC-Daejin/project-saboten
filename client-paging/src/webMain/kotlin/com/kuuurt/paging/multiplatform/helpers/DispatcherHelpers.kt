package com.kuuurt.paging.multiplatform.helpers

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 03/05/2020
 */

@OptIn(InternalCoroutinesApi::class)
actual fun dispatcher(): CoroutineDispatcher = Dispatchers.Default