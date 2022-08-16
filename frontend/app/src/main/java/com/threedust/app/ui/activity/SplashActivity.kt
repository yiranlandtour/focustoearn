package com.threedust.app.ui.activity

import android.Manifest
import android.content.Intent
import android.view.View
import android.view.WindowManager
import com.threedust.app.MyApp
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.ui.dialog.PolicyDialog
import com.threedust.app.utils.ConfigUtils
import com.threedust.app.utils.SysUtils
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity : BaseActivity() {

    private var mAdClicked: Boolean = false
    private var mPermissionConfirmed: Boolean = false

    private val mPermissions = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE
    )

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun initData() {
        requestConfig()
        //PolicyDialog().show(this, object : PolicyDialog.OnClickPolicyListener {
        //    override fun onClickCancel(v: View?) {
        //        //this@SplashActivity.finish()
        //        requestConfig()
        //    }
//
        //    override fun onClickSure(v: View?) {
        //        //checkPermission()
        //        requestConfig()
        //    }
        //})
    }

    override fun retryRequest() {
        requestConfig()
    }

    private fun requestConfig() {
        // 直接进入main页面
        //addSubscription(mApi.getAppConf(SysUtils.getVersionCode()), {
        //    MyApp.appConf = it
        //    if (ConfigUtils.isFirstUseApp()) {
        //        ConfigUtils.storeTaskList(it.task_list)
        //        ConfigUtils.setNotFirstUseApp()
        //    } else {
        //        MyApp.appConf.task_list = ConfigUtils.readTaskList()
        //    }
        //    SysUtils.postTaskDelay(Runnable {
        //        goMainActivity()
        //    }, 1000)
        //}, {
        //    SysUtils.postTaskDelay(Runnable {
        //        goMainActivity()
        //    }, 1000)
        //})
        if (ConfigUtils.isFirstUseApp()) {
            ConfigUtils.addUserCoinCount(10f)
            ConfigUtils.setNotFirstUseApp()
        }
        MyApp.user = ConfigUtils.getUser()
        SysUtils.postTaskDelay(Runnable {
            goMainActivity() }, 1000)
    }

    private fun checkPermission() {
        EasyPermissions.requestPermissions(
            this,
            resources.getString(R.string.need_permisions),
            0,
            *mPermissions
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode != 0 || perms.isEmpty()) {
            //finish()
            //return
        }
        for (perm in mPermissions) {
            if (!perms.contains(perm)) {
                //finish()
                //return
            }
        }
        mPermissionConfirmed = true

        requestConfig()
    }

    fun goMainActivity() {
        //iv_splash_holder.visibility = View.GONE
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        if (mAdClicked)
            goMainActivity()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}