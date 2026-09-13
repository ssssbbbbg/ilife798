package com.github.ilife798

// iOS 上无系统 Toast；业务层提示交由 UI 层呈现，这里仅记录日志兜底。
actual fun showToast(message: String) {
    logDebug("ILife798", "toast: $message")
}

actual fun dismissToast() {}