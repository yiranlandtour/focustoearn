package com.threedust.app.ui.activity

import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.utils.Logger
import kotlinx.android.synthetic.main.activity_wallet.*

class TransactionActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_wallet

    override fun initView() {
        iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        val data = intent.data
        Logger.d(data?.queryParameterNames)
        Logger.d(data?.getQueryParameter("errorCode"))
        Logger.d(data?.getQueryParameter("errorMessage"))
    }

    override fun retryRequest() {

    }
}