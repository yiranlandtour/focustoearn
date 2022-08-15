package com.threedust.app.api

import com.threedust.app.model.entity.AppConf
import com.threedust.app.model.entity.User
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author zzh
 * desc:
 */

interface ApiService {

    @POST("GetAppConf")
    fun getAppConf(@Query("version") version: Int): Observable<BaseResponse<AppConf>>

    @POST("GetUser")
    fun getUser(@Query("wallet_id") walletId: String): Observable<BaseResponse<User>>
}