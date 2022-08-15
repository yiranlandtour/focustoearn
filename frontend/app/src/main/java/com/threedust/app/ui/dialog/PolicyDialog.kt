package com.threedust.app.ui.dialog

import android.app.Dialog
import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import com.threedust.app.R
import com.threedust.app.ui.activity.WebActivity
import com.threedust.app.utils.ClickUtils
import com.threedust.app.utils.ConfigUtils
import com.threedust.app.utils.SysUtils
import com.threedust.app.utils.UIUtils

/**
 * Created by zzh
 */

class PolicyDialog {

    fun show(context: Context, onClickPolicyListener: OnClickPolicyListener) {
        if (hasShowRule(context)) {
            onClickPolicyListener.onClickSure(null)
            return
        }

        putShowRule(context)

        val content: String = "   You are welcome to use this APP. We attach great importance to your user rights and personal information protection. You can learn about our collection, use, storage and sharing of personal information, as well as your related rights, through the 《User Service Agreement》 and 《Privacy Policy》.\n" +
                "In order to provide basic services, you need to connect to the Internet and call your permissions as follows, and collect necessary personal information:\n" +
                "1. Obtain your mobile phone information to distinguish different devices;\n" +
                "2. Obtain read and write permissions on the phone to save and access cached content;\n" +
                "   Please click the Agree and Continue button in order to use this APP normally."

        val dialog = Dialog(context, R.style.dialog_style)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_policy)

        val tv_ok = dialog.findViewById<TextView>(R.id.tv_ok)
        val tv_cancel = dialog.findViewById<TextView>(R.id.tv_cancel)
        val tvContent = dialog.findViewById<TextView>(R.id.tv_text)

        tvContent.movementMethod = ScrollingMovementMethod.getInstance()
        tvContent.movementMethod = LinkMovementMethod.getInstance()

        val tag1 = "《"
        val tag2 = "》"

        val agreementLeftIdx = content.indexOf(tag1)
        val agreementRightIdx = content.indexOf(tag2) + 1
        val privacyLeftIdx = content.indexOf(tag1, agreementLeftIdx + 1)
        val privacyRightIdx = content.indexOf(tag2, agreementRightIdx + 1) + 1
        val linkColor1 = ForegroundColorSpan(SysUtils.getResourceColor(R.color.colorPrimary))
        val linkColor2 = ForegroundColorSpan(SysUtils.getResourceColor(R.color.colorPrimary))

        val style = SpannableStringBuilder()
        style.append(content)
        style.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                if (ClickUtils.notErrorClick())
                    context.startActivity(WebActivity.createIntent(context, ConfigUtils.getAppConf().user_policy_url, "User Service Agreement"))
            }
        }, agreementLeftIdx, agreementRightIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                if (ClickUtils.notErrorClick())
                    context.startActivity(WebActivity.createIntent(context, ConfigUtils.getAppConf().privacy_policy_url, "Privacy Policy"))
            }
        }, privacyLeftIdx, privacyRightIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.setSpan(linkColor1, agreementLeftIdx, agreementRightIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.setSpan(linkColor2, privacyLeftIdx, privacyRightIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvContent.text = style

        tv_ok.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                onClickPolicyListener.onClickSure(v)
                putShowRule(context)
                dialog.dismiss()
            }
        })
        tv_cancel.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                onClickPolicyListener.onClickCancel(v)
                dialog.dismiss()
            }
        })

        dialog.window?.setLayout(UIUtils.screenWidthPx * 4 / 5, UIUtils.screenHeightPx * 3 / 5)
        dialog.show()
    }

    fun hasShowRule(context: Context): Boolean {
        return ConfigUtils.getBoolean("privacy_rule", false)
        //return false
    }
    fun putShowRule(context: Context) {
        ConfigUtils.putBoolean("privacy_rule", true)
    }

    // 回调接口
    interface OnClickPolicyListener {
        fun onClickCancel(v: View?)
        fun onClickSure(v: View?)
    }
}