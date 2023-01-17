package io.denix.project.universaltunnel.common

import android.content.res.Resources

data class ScreenSizeInfo(var widthPixels: Int, var heightPixels: Int,
                          var density: Float,
                          var widthDp: Int, var heightDp: Int)

object ScreenSizeUtil {
    val screenSizeInfo = ScreenSizeInfo(0, 0, 0f, 0, 0)

    fun calculateScreenSize(resources: Resources): ScreenSizeInfo {
        screenSizeInfo.density = resources.displayMetrics.density

        screenSizeInfo.widthPixels = resources.displayMetrics.widthPixels
        screenSizeInfo.heightPixels = resources.displayMetrics.heightPixels

        screenSizeInfo.widthDp = (screenSizeInfo.widthPixels / screenSizeInfo.density).toInt()
        screenSizeInfo.heightDp = (screenSizeInfo.heightPixels / screenSizeInfo.density).toInt()

        return screenSizeInfo
    }
}