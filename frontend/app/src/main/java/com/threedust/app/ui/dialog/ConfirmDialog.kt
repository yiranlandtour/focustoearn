package com.threedust.app.ui.dialog

import android.content.Context
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_confirm.*

class ConfirmDialog(context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.dialog_confirm)
        okBtnClickListener()
        noBtnClickListener()
    }

    fun title(titleStr: String): ConfirmDialog {
        tv_confirm_title.text = titleStr
        return this
    }

    fun okBtnClickListener(listener: (()->Unit)? = null): ConfirmDialog {
        tv_btn_Yes.setOnClickListener {
            listener?.let { it() }
            dismiss()
        }
        return this
    }

    fun noBtnClickListener(listener: (()->Unit)? = null): ConfirmDialog {
        tv_btn_No.setOnClickListener {
            listener?.let { it() }
            dismiss()
        }
        return this
    }
}