package com.threedust.app.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import com.threedust.app.listener.OneArgListener
import com.threedust.app.ui.widget.ScrollPickerView
import kotlinx.android.synthetic.main.dialog_minute_picker.*

class MinutePickerDialog(context: Context) : BaseDialog(context) {

    var mMinute = 0

    init {
        setContentView(R.layout.dialog_minute_picker)
    }

    fun build(listener: OneArgListener?) : Dialog {
        sp_minute.layoutManager = LinearLayoutManager(context)

        val dataList = ArrayList<String>()
        for (i in 1..60) {
            dataList.add(if (i < 10) "0$i" else i.toString())
        }

        val scrollPickerAdapter = ScrollPickerView.ScrollPickerAdapterBuilder<String>(context)
            .setDataList(dataList)
            .selectedItemOffset(1)
            .setOnScrolledListener(object : OneArgListener {
                override fun trigger(arg: Any) {
                    val v = arg as View
                    v.tag?.let {
                        val minuteText = it as String
                        //SysUtils.showToast(minuteText)
                        mMinute = minuteText.toInt()
                    }
                }
            }).build()

        sp_minute.adapter = scrollPickerAdapter
        sp_minute.scrollToPosition(16)

        tv_btn_Yes.setOnClickListener {
            //SysUtils.showToast("select: " + mMinute)
            listener?.trigger(mMinute)
            dismiss()
        }
        return this
    }

}