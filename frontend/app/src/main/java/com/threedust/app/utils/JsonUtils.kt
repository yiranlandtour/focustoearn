package com.threedust.app.utils

import com.google.gson.Gson

object JsonUtils {
    private var gson = Gson()

    fun toJson(obj: Any): String {
        //Logger.printD(obj)
        var ret: String = ""
        try {
            ret = gson.toJson(obj)
        } catch (e: Exception) {

        }
        return ret
    }

    fun <T> fromJson(json: String?, classOfT: Class<T>): T? {
        var ret: T? = null
        try {
            ret = gson.fromJson(json!!, classOfT)
        } catch (e: Exception) {

        }
        return ret
    }
}