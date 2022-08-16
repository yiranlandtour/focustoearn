package com.threedust.app.ui.dialog

import android.content.Context
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import com.threedust.app.utils.SysUtils
import kotlinx.android.synthetic.main.dialog_sell.one_btn_Sub
import kotlinx.android.synthetic.main.dialog_username.*

class UsernameDialog(context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.dialog_username)
        okBtnClickListener()
    }

    fun okBtnClickListener(listener: ((String) -> Unit)? = null): UsernameDialog {
        one_btn_Sub.setOnClickListener {
            listener?.let {
                val username = et_username.text.toString()
                if (username.isEmpty()) {
                    SysUtils.showToast("Username can not be empty.")
                } else {
                    it(username)
                    dismiss()
                }
            }
        }
        return this
    }

    fun username(name: String): UsernameDialog {
        et_username.setText(name)
        return this
    }
}