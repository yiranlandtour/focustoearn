package com.threedust.app.ui.dialog

import android.content.Context
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import com.threedust.app.utils.SysUtils
import kotlinx.android.synthetic.main.dialog_confirm.*
import kotlinx.android.synthetic.main.dialog_sell.*
import java.lang.Exception

class SellDialog(context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.dialog_sell)
        okBtnClickListener()
    }

    fun okBtnClickListener(listener: ((price: Float) -> Unit)? = null): SellDialog {
        one_btn_Sub.setOnClickListener {
            listener?.let {
                try {
                    var sellPrice = et_sell_price.text.toString().toFloat()
                    if (sellPrice > 0) {
                        it (sellPrice)
                        dismiss()
                    } else {
                        SysUtils.showToast("The sell price should be greater than 0.")
                    }
                } catch (e: Exception) {
                    SysUtils.showToast("Please input a number.")
                }
            }
        }
        return this
    }

    fun sellPrice(price: Float): SellDialog {
        et_sell_price.setText(price.toString())
        return this
    }
}