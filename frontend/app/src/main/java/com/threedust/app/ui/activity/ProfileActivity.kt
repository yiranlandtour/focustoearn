package com.threedust.app.ui.activity

import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_market.*

class ProfileActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_profile

    override fun initView() {
        iv_back.setOnClickListener { finish() }
    }

    override fun initData() {

    }

    override fun retryRequest() {

    }
}