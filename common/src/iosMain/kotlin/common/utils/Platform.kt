package common.utils

import kotlin.native.Platform

actual object Platform {
    actual val isDevelopmentMode: Boolean = Platform.isDebugBinary
}