package com.threedust.app.utils

import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.widget.Toast
import com.threedust.app.MyApp
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * Created by zzh
 * desc:
 */

object SysUtils {
    // resources
    val resources: Resources
        get() {
            return MyApp.getContext().resources
        }

    // package name
    val packageName: String
        get() {
            return MyApp.getContext().packageName
        }

    fun getString(resId: Int): String {
        return resources.getString(resId)
    }

    fun postTaskSafely(task: Runnable) {
        val curThreadId = android.os.Process.myTid()
        if (curThreadId == MyApp.getMainThreadId()) {
            task.run()
        } else {
            MyApp.getMainHandler().post(task)
        }
    }

    fun postTaskDelay(task: Runnable, delayMillis: Int): Boolean {
        return MyApp.getMainHandler().postDelayed(task, delayMillis.toLong())
    }

    fun getUUID(): String {
        var uuid = ConfigUtils.getString("uuid", "")
        if (uuid == "") {
            val plainText = "" + UUID.randomUUID() + Build.MANUFACTURER + Build.MODEL + Build.BRAND
            uuid = md5(plainText)
            ConfigUtils.putString("uuid", uuid)
        }
        return uuid
    }

    // md5
    fun md5(plainText: String): String {
        var secretBytes: ByteArray? = null
        secretBytes = try {
            MessageDigest.getInstance("md5").digest(
                plainText.toByteArray()
            )
        } catch (e: NoSuchAlgorithmException) {
            return plainText.substring(0, 32)
        }
        var md5code = BigInteger(1, secretBytes).toString(16) //
        for (i in 0 until 32 - md5code.length) {
            md5code = "0$md5code"
        }
        return md5code
    }

    fun getAppName(): String {
        val ctx = MyApp.getContext()
        try {
            val packageManager = ctx.packageManager;
            val appInfo = packageManager.getApplicationInfo(
                ctx.packageName, PackageManager.GET_META_DATA
            )
            return appInfo.loadLabel(packageManager) as String
        } catch (e : Exception) {
            e.printStackTrace();
        }
        return ""
    }

    fun getUmengChannel(): String {
        return getManifestArg("UMENG_CHANNEL")
    }

    fun getManifestArg(arg: String): String {
        val ctx = MyApp.getContext()
        try {
            val packageManager = ctx.packageManager;
            val appInfo = packageManager.getApplicationInfo(
                ctx.packageName, PackageManager.GET_META_DATA
            )
            return appInfo.metaData.getString(arg) as String
        } catch (e : Exception) {
            e.printStackTrace();
        }
        return ""
    }

    fun getResourceColor(rid: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(rid, null)
        } else {
            resources.getColor(rid)
        }
    }

    fun getVersionCode(): Int {
        val ctx = MyApp.getContext()
        try {
            val packageManager = ctx.packageManager;
            val packageInfo = packageManager.getPackageInfo(ctx.packageName, 0)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toInt()
            } else {
                return packageInfo.versionCode
            }
        } catch (e : Exception) {
            e.printStackTrace();
        }
        return 0
    }

    fun showToast(msg: String) {
        Toast.makeText(MyApp.getContext(), msg, Toast.LENGTH_SHORT).show()
    }

}