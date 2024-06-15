import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinCocoapods)
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    val xcf = XCFramework()

    cocoapods {
        version = "1.0.0"
        ios.deploymentTarget = "13.0"
        pod("AppsFlyerFramework") {
            moduleName = "AppsFlyerLib"
            version = "6.12.2"
        }
        pod("YandexMobileMetrica") {
            moduleName = "YandexMobileMetrica"
            version = "4.5.2"
        }
        framework {
            transitiveExport = true
            isStatic = false
            xcf.add(this)
            embedBitcode(Framework.BitcodeEmbeddingMode.DISABLE)
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.mytestapplication"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
