package com.github.ilife798

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray.toImageBitmap(): ImageBitmap {
    // 将图片字节解码为 Skia Image，直接得到 Compose ImageBitmap。
    val image = Image.makeFromEncoded(this)
    return image.toComposeImageBitmap()
}