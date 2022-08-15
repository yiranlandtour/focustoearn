package com.threedust.app.utils

import android.util.Log

object Logger {
    val logTag = "zzh"
    val DEBUG = true
    //val DEBUG = false

    fun printD(msg: Any?) {
        if (!DEBUG) return
        val stackTrace = Thread.currentThread().stackTrace;
        val invokeEle = stackTrace[4]
        Log.d(logTag,"(" + invokeEle.fileName + ":" + invokeEle.lineNumber + ") - " + msg?.toString() )
    }
    fun d(vararg msgs: Any?) {
        msgs.forEach { printD(it) }
    }

    fun printE(msg: Any?) {
        val stackTrace = Thread.currentThread().stackTrace;
        val invokeEle = stackTrace[4]
        Log.e(logTag,"(" + invokeEle.fileName + ":" + invokeEle.lineNumber + ") - " + msg?.toString() )
    }
    fun e(vararg msgs: Any?) {
        msgs.forEach { printE(it) }
    }
}