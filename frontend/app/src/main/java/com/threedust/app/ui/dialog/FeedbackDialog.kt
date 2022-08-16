package com.threedust.app.ui.dialog

import android.content.Context
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import com.threedust.app.utils.SysUtils
import kotlinx.android.synthetic.main.dialog_feedback.*

class FeedbackDialog (context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.dialog_feedback)
        okBtnClickListener()
    }

    fun okBtnClickListener(listener: ((String) -> Unit)? = null): FeedbackDialog {
        one_btn_Sub.setOnClickListener {
            listener?.let {
                val feedback = et_feedback.text.toString()
                if (feedback.isEmpty()) {
                    SysUtils.showToast("Feedback can not be empty.")
                } else {
                    it(feedback)
                    SysUtils.showToast("Thanks for your feedback.")
                    dismiss()
                }
            }
        }
        return this
    }
}