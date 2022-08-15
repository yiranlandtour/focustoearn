package com.threedust.app.ui.dialog

import android.content.Context
import android.graphics.Typeface
import android.view.View
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_beauty.*


class BeautyDialog(context: Context) : BaseDialog(context, R.style.pic_dialog_style) {

    init {
        setContentView(R.layout.dialog_beauty)
        noBtnClickListener()
        okBtnClickListener()
    }

    fun headImage(imgResourceId : Int) : BeautyDialog {
        iv_title_img.setImageResource(imgResourceId)
        return this
    }

    fun title(titleStr: String): BeautyDialog {
        tv_title.text = titleStr
        return this
    }

    fun titleBold(isBold: Boolean): BeautyDialog {
        var boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD)
        if (!isBold) boldTypeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        tv_title.typeface = boldTypeface
        return this
    }

    fun showCoinImg(): BeautyDialog {
        iv_coin.visibility = View.VISIBLE
        return this
    }

    fun coinNum(coinNum: Float): BeautyDialog {
        tv_coin_num.text = coinNum.toString()
        tv_coin_num.visibility = View.VISIBLE
        return this
    }

    fun show2Btn() : BeautyDialog {
        rl_two_btn.visibility = View.VISIBLE
        tv_one_btn_Yes.visibility = View.GONE
        return this
    }

    fun okBtnClickListener(listener: (()->Unit)? = null): BeautyDialog {
        tv_one_btn_Yes.setOnClickListener {
            listener?.let { it() }
            dismiss()
        }
        tv_btn_Yes.setOnClickListener {
            listener?.let { it() }
            dismiss()
        }
        return this
    }

    fun noBtnClickListener(listener: (()->Unit)? = null): BeautyDialog {
        tv_btn_No.setOnClickListener {
            listener?.let { it() }
            dismiss()
        }
        return this
    }

    fun okBtnTitle(title: String): BeautyDialog {
        tv_one_btn_Yes.text = title
        tv_btn_Yes.text = title
        return this
    }
}