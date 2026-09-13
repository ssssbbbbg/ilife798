package com.github.ilife798.pay

// iOS 端暂不内置支付宝 SDK；返回明确提示，由上层提示用户。
// 如需接入，可在 iOS 端集成支付宝移动支付 SDK（AlipaySDK-iOS）后在此实现。
actual suspend fun payWithAlipay(orderInfo: String): AlipayPayResult =
    AlipayPayResult(false, "iOS 端暂未接入支付宝支付")