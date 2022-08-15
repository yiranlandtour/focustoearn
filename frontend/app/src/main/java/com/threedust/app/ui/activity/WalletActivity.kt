package com.threedust.app.ui.activity

import android.util.Log
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.model.Constant
import com.threedust.app.utils.Logger
import com.threedust.app.utils.nacl.NaCl
import kotlinx.android.synthetic.main.activity_wallet.*
import org.bitcoinj.core.Base58

class WalletActivity : BaseActivity() {

    override fun layoutId(): Int = R.layout.activity_wallet

    override fun initView() {
        iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        val data = intent.data
        Logger.d(data?.queryParameterNames)
        val key = data?.getQueryParameter("phantom_encryption_public_key")
        val nonce = data?.getQueryParameter("nonce")
        val dat = data?.getQueryParameter("data")

        //val sharedSecretDapp = NaCl.box.before(Base58.decode(key), Constant.secretKey.toByteArray())
        Logger.d(Base58.decode(key))
        Logger.d(Base58.encode(Constant.keyPair.first))
        Logger.d(Base58.encode(Constant.keyPair.second))
        val connectData = NaCl.box.open(Base58.decode(dat), Base58.decode(nonce), Base58.decode(key), Constant.keyPair.second)

        val dataString = connectData?.let { String(it) }
        Logger.d(dataString)

        val walletData = Base58.decode(data?.getQueryParameter("data"))
        Logger.d()
        Logger.d(data?.toString())
    }

    override fun retryRequest() {

    }

}