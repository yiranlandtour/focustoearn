package com.threedust.app.ui.dialog

import android.content.Context
import android.content.Intent
import android.view.Gravity
import com.google.android.flexbox.*
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import com.threedust.app.model.entity.TaskItem
import com.threedust.app.ui.activity.TodoListActivity
import com.threedust.app.ui.adapter.RVTaskNameAdapter
import com.threedust.app.ui.widget.recyclerview.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.dialog_bottom.*

class BottomDialog(context: Context) : BaseDialog(context, R.style.dialog_bottom_style) {

    lateinit var mTaskNameItemAdapter : RVTaskNameAdapter
    var mItemClickListener: ((taskName: TaskItem) -> Unit)? = null

    init {
        setContentView(R.layout.dialog_bottom)
        initView()
    }

    fun itemClickListener(listener: (taskName: TaskItem) -> Unit) : BottomDialog {
        mItemClickListener = listener
        return this
    }

    fun initView() {

        window?.setGravity(Gravity.BOTTOM)
        val lp = window?.attributes
        lp?.y = 0
        window?.attributes = lp

        btn_edit.setOnClickListener {
            context.startActivity(Intent(context, TodoListActivity::class.java))
        }

        val taskNameArr = arrayListOf<TaskItem>(
            TaskItem(1, "Study", "green"),
            TaskItem(2, "Play", "pink"),
            TaskItem(3, "Work", "blue"),
            TaskItem(4, "Football", "yellow"),
            TaskItem(5, "Sleep", "red"))

        mTaskNameItemAdapter = RVTaskNameAdapter(context, taskNameArr)
        val flexBoxLM = FlexboxLayoutManager(context)
        flexBoxLM.flexWrap = FlexWrap.WRAP
        flexBoxLM.flexDirection = FlexDirection.ROW
        flexBoxLM.alignItems = AlignItems.FLEX_START
        flexBoxLM.justifyContent = JustifyContent.FLEX_START

        rv_task_name.layoutManager = flexBoxLM
        rv_task_name.adapter = mTaskNameItemAdapter

        mTaskNameItemAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Any?, position: Int) {
                mItemClickListener?.let { it(taskNameArr[position]) }
                dismiss()
            }
        })
    }
}