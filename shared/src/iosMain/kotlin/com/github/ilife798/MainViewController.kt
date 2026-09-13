package com.github.ilife798

import androidx.compose.ui.window.ComposeUIViewController
import com.github.ilife798.data.api.ApiConfig
import platform.UIKit.UIViewController

// iOS 应用入口：初始化存储与业务配置后，挂载 Compose UI。
// 由 Xcode 工程（iosApp）在 AppDelegate / SwiftUI 中调用此函数。
@Suppress("unused")
fun MainViewController(): UIViewController {
    AppStorage.instance = PersistentStorage()
    ApiConfig.init(
        gateway = BuildKonfig.GATEWAY,
        salt = BuildKonfig.SIGN_SALT,
        clientId = BuildKonfig.API_CID,
    )
    return ComposeUIViewController { App() }
}