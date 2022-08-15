package com.threedust.app.utils

import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import com.threedust.app.MyApp

/**
 * Created by zzh
 * desc:
 */

object UIUtils {
    // resources
    val resources: Resources
        get() {
            return MyApp.getContext().resources
        }
    // density
    val density: Float
        get() {
            return resources.displayMetrics.density
        }

    val screenWidthPx: Int
        get() {
            return resources.displayMetrics.widthPixels
        }

    val screenHeightPx: Int
        get() {
            return resources.displayMetrics.heightPixels
        }

    val screenWidthDp: Float
        get() {
            return px2dp(screenWidthPx)
        }

    val screenHeightDp: Float
        get() {
            return px2dp(screenHeightPx)
        }

    fun dp2px(dp: Float): Int {
        return (dp * density + 0.5).toInt()
    }
    fun px2dp(px: Int): Float {
        return px / density
    }
    fun sp2px(sp: Int): Int {
        return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), resources.displayMetrics) + 0.5).toInt()
    }
}