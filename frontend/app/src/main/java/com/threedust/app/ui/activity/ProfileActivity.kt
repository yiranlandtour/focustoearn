package com.threedust.app.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.threedust.app.MyApp
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.ui.dialog.FeedbackDialog
import com.threedust.app.ui.dialog.UsernameDialog
import com.threedust.app.utils.ConfigUtils
import com.threedust.app.utils.SysUtils
import kotlinx.android.synthetic.main.activity_market.*
import kotlinx.android.synthetic.main.activity_market.iv_back
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_username.view.*

class ProfileActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_profile

    override fun initView() {
        iv_back.setOnClickListener { finish() }

        tv_user_name.text = MyApp.user.nick_name
        tv_wallet_id.text = ConfigUtils.getUserWalletPubKey()
        ll_wallet_id.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Label", ConfigUtils.getUserWalletPubKey())
            clipboardManager.setPrimaryClip(clipData)
            SysUtils.showToast("Wallet public key copied to clipboard.")
        }

        ll_user_name.setOnClickListener {
            UsernameDialog(this).username(MyApp.user.nick_name).okBtnClickListener {
                MyApp.user.nick_name = it
                tv_user_name.text = it
            }.show()
        }

        ll_feedback.setOnClickListener {
            FeedbackDialog(this).okBtnClickListener {

            }.show()
        }

    }

    override fun initData() {

    }

    override fun retryRequest() {

    }
}