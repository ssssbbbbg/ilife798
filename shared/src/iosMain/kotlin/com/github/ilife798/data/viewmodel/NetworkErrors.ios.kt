package com.github.ilife798.data.viewmodel

import platform.Foundation.NSError
import platform.Foundation.NSURLErrorCannotFindHost
import platform.Foundation.NSURLErrorCannotConnectToHost
import platform.Foundation.NSURLErrorDomain
import platform.Foundation.NSURLErrorNetworkConnectionLost
import platform.Foundation.NSURLErrorNotConnectedToInternet
import platform.Foundation.NSURLErrorTimedOut

// 把 DNS 解析失败、连接超时等瞬时网络异常视为可重试；SSL 证书错误不重试。
internal actual fun isTransientNetworkError(e: Throwable): Boolean {
    val code = (e as? NSError)?.code
    if (code != null) {
        return when (code) {
            NSURLErrorCannotFindHost,
            NSURLErrorCannotConnectToHost,
            NSURLErrorNetworkConnectionLost,
            NSURLErrorNotConnectedToInternet,
            NSURLErrorTimedOut,
            -> true

            else -> false
        }
    }
    val cause = e.cause ?: return false
    return cause !== e && isTransientNetworkError(cause)
}