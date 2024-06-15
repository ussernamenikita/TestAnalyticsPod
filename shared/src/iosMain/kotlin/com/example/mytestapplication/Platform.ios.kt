package com.example.mytestapplication

import cocoapods.YandexMobileMetrica.YMMMutableUserProfile
import cocoapods.YandexMobileMetrica.YMMProfileAttribute
import cocoapods.YandexMobileMetrica.YMMYandexMetrica
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIDevice


class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalForeignApi::class)
fun YandexMetricsProfile.report(logger: Logger, tag: String) {
    val userProfileBuilder = YMMMutableUserProfile()
    numbers.forEach {
        userProfileBuilder.apply(YMMProfileAttribute.customNumber(it.first).withValue(it.second))
    }
    strings.forEach {
        userProfileBuilder.apply(YMMProfileAttribute.customString(it.first).withValue(it.second))
    }
    YMMYandexMetrica.reportUserProfile(userProfileBuilder) {
        logger.log(tag, "report error $it, for report profile")
    }
}

class YandexMetricsProfile(
    val numbers: MutableList<Pair<String, Double>> = mutableListOf(),
    val strings: MutableList<Pair<String, String>> = mutableListOf(),
)


interface Logger {
    fun log(tag: String, message: String)
    fun log(error: Throwable)
    fun logIfDebug(isDebug: Boolean, tag: String, value: () -> String)
}
