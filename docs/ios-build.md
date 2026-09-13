# iOS 版本构建说明

本项目在 `shared` 模块新增了 iOS 目标（`iosArm64` / `iosSimulatorArm64`），
并提供了 `iosApp` Xcode 工程与 GitHub Actions 工作流。

## 目录结构

- `shared/src/iosMain/kotlin/` —— iOS 平台 `actual` 实现（剪贴板、存储、HTTP、时间、通知等）
- `shared/src/iosMain/kotlin/com/github/ilife798/MainViewController.kt` —— iOS 入口
- `iosApp/` —— SwiftUI + Xcode 工程
- `.github/workflows/ios.yml` —— iOS 构建/发布工作流

## 本地构建（macOS + Xcode）

```bash
# 1. 配置业务密钥
cp secrets.properties.example secrets.properties
# 编辑 secrets.properties 填入 API_GATEWAY / SIGN_SALT / API_CID

# 2. 打开 Xcode 工程，选择 iosApp scheme 与真机/模拟器运行
open iosApp/iosApp.xcodeproj
```

或命令行构建（模拟器，无需签名）：

```bash
./gradlew :shared:compileKotlinIosSimulatorArm64
xcodebuild -project iosApp/iosApp.xcodeproj -scheme iosApp \
  -configuration Debug -sdk iphonesimulator \
  -destination 'generic/platform=iOS Simulator' \
  CODE_SIGNING_ALLOWED=NO build
```

## GitHub Actions 打包 IPA

推送 `v*` 标签即触发 iOS 发布工作流，产出 `.ipa` 并上传到 GitHub Release。

需要在仓库 Secrets 配置：

| Secret | 说明 |
| --- | --- |
| `API_GATEWAY` | API 网关地址 |
| `SIGN_SALT` | 积分签名盐值 |
| `API_CID` | 客户端标识 |
| `IOS_DEVELOPMENT_TEAM` | Apple 开发者团队 ID |
| `IOS_PROVISIONING_PROFILE_BASE64` | 描述文件（.mobileprovision）Base64 |
| `IOS_PROVISIONING_PROFILE_NAME` | 描述文件名称 |
| `IOS_CERTIFICATE_BASE64` | 签名证书（.p12）Base64 |
| `IOS_CERTIFICATE_PASSWORD` | 证书密码 |

> 证书需为 Apple Development / Distribution 类型；`ExportOptions.plist` 默认 `method = app-store`，
> 如需 ad-hoc 分发请改为 `ad-hoc`。

## 平台差异说明

| 功能 | Android | iOS |
| --- | --- | --- |
| 设备控制 / 积分任务 / 账单 / 登录 | ✅ | ✅ |
| 支付宝充值 | ✅ | ❌（占位，返回提示） |
| 二维码扫描 | ✅（CameraX + ZXing） | ⚠️（占位，可接 AVFoundation） |
| 后台运行通知 / 快捷图块 | ✅ | ❌（iOS 无对应） |
| 应用内更新（APK） | ✅ | ❌（iOS 走 App Store） |
| 窗口级背景模糊 | ✅ | ❌（占位） |
