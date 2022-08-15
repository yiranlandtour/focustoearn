package com.threedust.app.rx.scheduler

/**
 * @author zzh
 * desc:
 */

object SchedulerUtils {

    fun<T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}