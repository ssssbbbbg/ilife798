package com.github.ilife798.ui.page.device

import androidx.compose.runtime.Composable
import top.yukonga.miuix.kmp.basic.Text

// iOS 端二维码扫描占位实现。识别逻辑见 commonMain 的 QrCodeParser。
// 如需原生相机扫码，可集成 AVFoundation 后在此实现。
@Composable
actual fun QrScannerPage(
    onBack: () -> Unit,
    onResult: (String) -> Unit,
) {
    // 占位：调用方会展示一个空页；此处仅渲染提示，不影响编译。
    Text("iOS 扫码功能待接入")
}