package com.threedust.app.ui.adapter

import android.content.Context
import android.widget.TextView
import com.threedust.app.R
import com.threedust.app.model.entity.TaskItem
import com.threedust.app.ui.widget.recyclerview.ViewHolder
import com.threedust.app.ui.widget.recyclerview.adapter.CommonAdapter

class RVTaskNameAdapter(ctx: Context, dataList: ArrayList<TaskItem>) :
    CommonAdapter<TaskItem>(ctx, dataList, R.layout.rv_item_task_name) {

    override fun bindData(holder: ViewHolder, data: TaskItem, position: Int) {
        with(holder) {
            val tv_task_name = getView<TextView>(R.id.tv_task_name)
            val resId = when (data.color) {
                "green" -> R.drawable.bg_task_name_green
                "pink" -> R.drawable.bg_task_name_pink
                "blue" -> R.drawable.bg_task_name_blue
                "yellow" -> R.drawable.bg_task_name_yellow
                "red" -> R.drawable.bg_task_name_red
                else -> R.drawable.bg_task_name_green
            }
            tv_task_name.setBackgroundResource(resId)
            tv_task_name.text = data.title
        }
    }
}