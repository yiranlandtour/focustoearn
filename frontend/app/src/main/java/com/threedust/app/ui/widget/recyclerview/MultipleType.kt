package com.threedust.app.ui.widget.recyclerview

/**
 * desc: multi layout
 */

interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}
