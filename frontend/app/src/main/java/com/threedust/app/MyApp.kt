package com.threedust.app

import android.app.Application
import android.content.Context
import android.os.Handler
import com.facebook.drawee.backends.pipeline.Fresco
import com.threedust.app.model.entity.AppConf
import com.threedust.app.model.entity.User
import com.threedust.app.utils.ConfigUtils
import com.threedust.app.utils.Logger
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import kotlin.properties.Delegates

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃
//    ┃　　　┃
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛
/**
 * Created by zzh
 * desc: application
 */

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
        var handler: Handler by Delegates.notNull()
        var user: User = User() //
        var appConf: AppConf = AppConf() // app info

        fun getContext(): Context {
            return instance.applicationContext
        }

        fun getMainThreadId(): Int {
            return android.os.Process.myTid()
        }

        fun getMainHandler(): Handler {
            return handler
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        handler = Handler()

        appConf.task_list = ConfigUtils.getTaskList()

        initConfig()
    }

    private fun initConfig() {
        Fresco.initialize(this)

        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                // Merely log undeliverable exceptions
                Logger.d(e.message)
            } else {
                // Forward all others to current thread's uncaught exception handler
                Thread.currentThread().also { thread ->
                    thread.uncaughtExceptionHandler.uncaughtException(thread, e)
                }
            }
        }
    }
}