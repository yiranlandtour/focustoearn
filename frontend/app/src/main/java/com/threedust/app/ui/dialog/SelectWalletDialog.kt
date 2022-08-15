package com.threedust.app.ui.dialog

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import com.threedust.app.model.Constant
import kotlinx.android.synthetic.main.dialog_wallet_select.*
import org.bitcoinj.core.Base58

class SelectWalletDialog(context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.dialog_wallet_select)
        walletBtnClickListener()
    }
    fun title(titleStr: String): SelectWalletDialog {
        tv_title.text = titleStr
        return this
    }

    fun walletBtnClickListener(listener: ((walletId: String)->Unit)? = null): SelectWalletDialog {
        tv_btn_wallet.setOnClickListener {
            val uriBuilder = Uri.parse("https://phantom.app/ul/v1/connect").buildUpon()
            uriBuilder.appendQueryParameter("app_url", "http://focuson.xuyaomaoxian.com")
                //.appendQueryParameter("dapp_encryption_public_key", "6mykzhCRDzJvFdfc6GxUWHgTEG5R6qcBQpY6UfK2Kg8n")
                .appendQueryParameter("dapp_encryption_public_key", Base58.encode(Constant.keyPair.first))
                .appendQueryParameter("redirect_link", "focuson://focuson.xuyaomaoxian.com")
                .appendQueryParameter("cluster", "devnet")
            val intent = Intent(ACTION_VIEW, uriBuilder.build())
            context.startActivity(intent)
            listener?.let {
                it("5g4495sdf343f32adfibukajlgkjntasadr")
            }
            dismiss()
        }
        return this
    }
}