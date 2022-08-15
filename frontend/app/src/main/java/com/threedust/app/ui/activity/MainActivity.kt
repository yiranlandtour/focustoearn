package com.threedust.app.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.threedust.app.MyApp
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.listener.OneArgListener
import com.threedust.app.model.Constant
import com.threedust.app.model.entity.TaskItem
import com.threedust.app.ui.dialog.*
import com.threedust.app.utils.*
import com.threedust.app.utils.nacl.NaCl
import kotlinx.android.synthetic.main.activity_main.*
import org.bitcoinj.core.Base58

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    var mIsInTask: Boolean = false
    var mMinute: Int = 0
    var mTask: TaskItem? = null
    var mTaskCoinNum: Float = 4.66f

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView() {
        nav_view.itemIconTintList = null // icon no color

        mTask = MyApp.appConf.task_list[0]

        tv_task_name.text = mTask?.title
        iv_left_menu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        iv_right_menu.setOnClickListener {
            startActivity(Intent(this@MainActivity, WalletActivity::class.java))
        }

        btn_select.setOnClickListener {
            BottomDialog(this).itemClickListener { taskName ->
                mTask = taskName
                tv_task_name.text = taskName.title
            }.show()
        }

        wg_timer.setOnClickListener {
            ClickUtils.notErrorClick {
                if (!mIsInTask) {
                    MinutePickerDialog(this).build(object : OneArgListener {
                        override fun trigger(minute: Any) {
                            mMinute = minute as Int
                            wg_timer.setMinute(mMinute)
                        }
                    }).show()
                }
            }
        }

        btn_start.setOnClickListener {
            if (!mIsInTask) {
                if (mMinute > 0) {
                    wg_timer.setSecondCountDown( { startTaskUI() }, {
                            endTaskUI()
                            BeautyDialog(this).headImage(R.mipmap.good_finger).title("Congratulations, keep it up!")
                                .showCoinImg().coinNum(mTaskCoinNum)
                                .okBtnClickListener{
                                    // check if the user has any land
                                    if (!ConfigUtils.isHaveLand()) {
                                        BeautyDialog(this@MainActivity).headImage(R.mipmap.land_marker)
                                            .title("You need a piece of land before planting a tree, go buy a piece of land?")
                                            .okBtnTitle("OK").okBtnClickListener{
                                                startActivity(Intent(this@MainActivity, LandActivity::class.java))
                                            }.show()
                                    }
                                }.show()
                            // make sound
                            // add coin to user's account
                        }
                    )
                } else {
                    SysUtils.showToast("Please set the countdown first ")
                }
            }
        }

        btn_close.setOnClickListener {
            if (mIsInTask) {
                wg_timer.pause()
                BeautyDialog(this).title("Are you sure to cancel?")
                    .show2Btn().noBtnClickListener { wg_timer.resume() }
                    .okBtnClickListener {
                        BeautyDialog(this@MainActivity).title("Next time will be better!").show()
                        endTaskUI()
                        wg_timer.stop()
                    }.show()
            }
        }

        val loginHeadView = nav_view.getHeaderView(0).findViewById<View>(R.id.ll_login_head)
        val walletHeadView = nav_view.getHeaderView(0).findViewById<View>(R.id.ll_wallet_head)
        loginHeadView.setOnClickListener {
            SelectWalletDialog(this)
                .title("Select Wallet")
                .walletBtnClickListener { walletId ->
                ConfigUtils.login(walletId)
                loginHeadView.visibility = View.GONE
                walletHeadView.visibility = View.VISIBLE
                tv_btn_logout.visibility = View.VISIBLE
            }.show()
        }

        tv_btn_logout.setOnClickListener {
            ConfirmDialog(this).
                title("Are you sure to logout?")
                .okBtnClickListener{
                    loginHeadView.visibility = View.VISIBLE
                    walletHeadView.visibility = View.GONE
                    tv_btn_logout.visibility = View.GONE
                }.show()
        }

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun startTaskUI() {
        iv_clock_bg.setImageResource(R.mipmap.bg_clock_ing)
        btn_start.visibility = View.GONE
        btn_select.visibility = View.GONE
        iv_left_menu.visibility = View.GONE
        iv_right_menu.visibility = View.GONE
        btn_close.visibility = View.VISIBLE
        tv_slogan.text = "No pain, no gain."
        mIsInTask = true
    }

    private fun endTaskUI() {
        iv_clock_bg.setImageResource(R.mipmap.bg_clock)
        btn_start.visibility = View.VISIBLE
        btn_close.visibility = View.GONE
        btn_select.visibility = View.VISIBLE
        iv_left_menu.visibility = View.VISIBLE
        iv_right_menu.visibility = View.VISIBLE
        tv_slogan.text = "Better late than never."
        mIsInTask = false
        mMinute = 0
    }

    override fun initData() {
        Logger.d(UIUtils.screenWidthDp, UIUtils.screenHeightDp)

        val keyPair = NaCl.box.keyPair()
        Logger.d(Base58.encode(keyPair.first).toString(), Base58.encode(keyPair.second).toString())
    }

    override fun retryRequest() {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_todo -> {
                startActivity(Intent(this, TodoListActivity::class.java))
            }
            R.id.nav_market -> {
                startActivity(Intent(this, MarketActivity::class.java))
            }
            R.id.nav_land -> {
                startActivity(Intent(this, LandActivity::class.java))
            }
            R.id.nav_my_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }
        //drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }
}