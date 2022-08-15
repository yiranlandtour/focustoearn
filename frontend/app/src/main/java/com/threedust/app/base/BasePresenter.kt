package com.threedust.app.base

import com.threedust.app.api.RetrofitMgr

/**
 * @author zzh
 * created:
 * desc:
 */

open class BasePresenter<T: IView>(view: T){

    var mApi = RetrofitMgr.api
    var mView: T = view
}