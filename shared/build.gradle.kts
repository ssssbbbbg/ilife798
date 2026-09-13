import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.spotless)
    alias(libs.plugins.buildkonfig)
}

aboutLibraries {
    export {
        outputFile = file("src/commonMain/composeResources/files/aboutlibraries.json")
        prettyPrint = true
    }
}

repositories {
    google {
        mavenContent {
            includeGroupByRegex("androidx(\\..*)?")
            includeGroupByRegex("com\\.android(\\..*)?")
            includeGroupByRegex("com\\.google(\\..*)?")
        }
    }
    mavenCentral()
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.github.ilife798.shared.resources"
}

val secretsProps =
    Properties().apply {
        val f = rootProject.file("secrets.properties")
        if (f.exists()) f.inputStream().use { load(it) }
    }

buildkonfig {
    packageName = "com.github.ilife798"

    defaultConfigs {
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "GATEWAY",
            "\"${secretsProps.getProperty("API_GATEWAY", "")}\"",
        )
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "SIGN_SALT",
            "\"${secretsProps.getProperty("SIGN_SALT", "")}\"",
        )
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "API_CID",
            "\"${secretsProps.getProperty("API_CID", "")}\"",
        )
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    android {
        namespace = "com.github.ilife798.shared"
        compileSdk = 37
        minSdk = 26

        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
        androidResources {
            enable = true
        }
        withHostTest {
            isIncludeAndroidResources = true
        }
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.okhttp)
            implementation(libs.androidx.activity.compose)
            implementation(libs.alipay.sdk)
            implementation(libs.androidx.camera.core)
            implementation(libs.androidx.camera.camera2)
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.androidx.camera.view)
            implementation(libs.zxing.core)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }
        commonMain.dependencies {
            @Suppress("DEPRECATION")
            implementation("org.jetbrains.compose.components:components-resources:${libs.versions.composeMultiplatform.get()}")
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.miuix.ui)
            implementation(libs.miuix.icons)
            implementation(libs.miuix.preference)
            implementation(libs.miuix.navigation)
            implementation(libs.miuix.blur)
            implementation(libs.miuix.shader)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.ktor.core)
            implementation(libs.ktorContentNegotiation)
            implementation(libs.ktorSerializationJson)
            implementation(libs.kotlinxSerializationJson)
            implementation(libs.aboutlibraries.compose.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// 依赖变化时自动重新生成开源许可清单，供 Compose Resources 读取
tasks
    .matching {
        it.name in
            setOf(
                "prepareComposeResourcesTaskForCommonMain",
                "copyNonXmlValueResourcesForCommonMain",
                "convertXmlValueResourcesForCommonMain",
            )
    }.configureEach {
        dependsOn("exportLibraryDefinitions")
    }
