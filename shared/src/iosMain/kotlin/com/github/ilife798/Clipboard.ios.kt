package com.github.ilife798

import platform.UIKit.UIPasteboard

actual fun copyTextToClipboard(text: String): Boolean =
    try {
        UIPasteboard.generalPasteboard.string = text
        true
    } catch (_: Exception) {
        false
    }