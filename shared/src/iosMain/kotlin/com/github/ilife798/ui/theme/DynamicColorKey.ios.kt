package com.github.ilife798.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// iOS 未提供系统强调色种子，返回 null 回退到 Miuix 平台动态色。
@Composable
actual fun systemDynamicColorKey(): Color? = null