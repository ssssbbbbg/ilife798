package com.github.ilife798

// iOS 端运行状态通知为空实现（Android 专属后台通知在 iOS 无对应）。
actual object RunNotifications {
    actual fun ensureInitialized() {}

    actual fun updateDevice(
        deviceId: String,
        deviceName: String,
        running: Boolean,
    ) {}

    actual fun updateTask(gained: Int) {}

    actual fun removeTask() {}
}