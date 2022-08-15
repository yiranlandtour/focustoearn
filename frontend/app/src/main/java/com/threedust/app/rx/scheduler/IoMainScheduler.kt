package com.threedust.app.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author zzh
 * desc:
 */

class IoMainScheduler<T>: BaseScheduler<T> (Schedulers.io(), AndroidSchedulers.mainThread())