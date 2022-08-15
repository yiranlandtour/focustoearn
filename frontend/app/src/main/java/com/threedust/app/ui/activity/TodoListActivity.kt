package com.threedust.app.ui.activity

import com.google.android.flexbox.*
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.model.entity.TaskItem
import com.threedust.app.ui.adapter.RVTaskNameAdapter
import com.threedust.app.ui.dialog.TaskEditDialog
import com.threedust.app.ui.widget.recyclerview.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.activity_todo_list.*

class TodoListActivity : BaseActivity() {

    lateinit var mTaskNameItemAdapter: RVTaskNameAdapter

    override fun layoutId(): Int = R.layout.activity_todo_list

    override fun initView() {

        iv_back.setOnClickListener { finish() }

        btn_add.setOnClickListener {
            TaskEditDialog(this).show()
        }

        val taskNameArr = arrayListOf<TaskItem>(
            TaskItem(1, "Study", "green"),
            TaskItem(2, "Play", "pink"),
            TaskItem(3, "Work", "blue"),
            TaskItem(4, "Football", "yellow"),
            TaskItem(5, "Sleep", "red")
        )

        mTaskNameItemAdapter = RVTaskNameAdapter(this, taskNameArr)
        val flexBoxLM = FlexboxLayoutManager(this)
        flexBoxLM.flexWrap = FlexWrap.WRAP
        flexBoxLM.flexDirection = FlexDirection.ROW
        flexBoxLM.alignItems = AlignItems.FLEX_START
        flexBoxLM.justifyContent = JustifyContent.FLEX_START

        rv_task_name.layoutManager = flexBoxLM
        rv_task_name.adapter = mTaskNameItemAdapter

        mTaskNameItemAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Any?, position: Int) {

            }
        })
    }

    override fun initData() {

    }

    override fun retryRequest() {

    }

}