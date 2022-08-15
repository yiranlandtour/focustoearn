package com.threedust.app.api

/**
 * @author zzh
 * desc:
 */

class BaseResponse<T> (val code: Int,
                       val msg: String,
                       val data: T) {
    companion object {
        const val SUCESS = 0
        const val ERROR = 1
    }
}