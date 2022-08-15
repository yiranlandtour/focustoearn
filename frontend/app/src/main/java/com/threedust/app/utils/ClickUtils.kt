package com.threedust.app.utils

object ClickUtils {
    var lastClickTime = 0L
    const val INTERVAL = 500

    fun notErrorClick(callback: (()->Unit)? = null): Boolean {
        var retValue = true
        val now = System.currentTimeMillis()
        if (now - lastClickTime < INTERVAL) {
            retValue =  false
        }
        lastClickTime = now
        if (retValue) callback?.let { it() }
        return true
    }

    fun isErrorClick(): Boolean { return !notErrorClick() }
}