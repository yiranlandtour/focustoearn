package com.threedust.app.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.threedust.app.MyApp
import com.threedust.app.R
import com.threedust.app.base.BaseDialog
import com.threedust.app.model.entity.TaskItem
import com.threedust.app.utils.ConfigUtils
import com.threedust.app.utils.TimeUtils
import kotlinx.android.synthetic.main.dialog_task_edit.*

class TaskEditDialog (context: Context) : BaseDialog(context) {

    var mTask: TaskItem? = null
    var mAddTaskCB: ((task: TaskItem)->Unit)? = null
    var mModifyTaskCB: ((task: TaskItem)->Unit)? = null
    var mDelTaskCB: ((task: TaskItem)->Unit)? = null

    init {
        setContentView(R.layout.dialog_task_edit)
    }

    @SuppressLint("SetTextI18n")
    fun build(): TaskEditDialog {
        mAddTaskCB?.let {
            val id = if (MyApp.appConf.task_list.isEmpty()) 1 else MyApp.appConf.task_list.last().id
            mTask = TaskItem(id, "New Task", "green")
            ll_summary.visibility = View.GONE
            rl_btn.visibility = View.GONE
        }

        mModifyTaskCB?.let {
            clearCheckIcon()
            when (mTask?.color) {
                "green" -> icl_color_green.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE
                "pink" -> icl_color_pink.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE
                "blue" -> icl_color_blue.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE
                "yellow" -> icl_color_yellow.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE
                "red" -> icl_color_red.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE
            }
            tv_times_num.text = mTask?.total_count.toString() + " times"
            tv_time_total.text = TimeUtils.getTimeText(mTask?.total_time)
            one_btn_Sub.visibility = View.GONE
        }

        icl_color_green.setOnClickListener { mTask?.color="green"; clearCheckIcon(); it.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE }
        icl_color_pink.setOnClickListener { mTask?.color="pink"; clearCheckIcon(); it.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE }
        icl_color_blue.setOnClickListener { mTask?.color="blue"; clearCheckIcon(); it.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE }
        icl_color_yellow.setOnClickListener { mTask?.color="yellow"; clearCheckIcon(); it.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE }
        icl_color_red.setOnClickListener { mTask?.color="red"; clearCheckIcon(); it.findViewById<View>(R.id.iv_check_mark).visibility = View.VISIBLE }

        et_task_name.addTextChangedListener {
            mTask?.title = et_task_name.text.toString()
        }

        mTask?.let {
            et_task_name.setText(it.title)
        }


        tv_btn_Del.setOnClickListener {
            BeautyDialog(context).show2Btn().title("Are you sure to delete?").headImage(R.mipmap.delete_icon)
                .okBtnClickListener {
                    mDelTaskCB?.let { it(mTask!!) }
                    dismiss()
                }.show()
        }

        tv_btn_Sub.setOnClickListener {
            mModifyTaskCB?.let { it(mTask!!) }
            dismiss()
        }

        one_btn_Sub.setOnClickListener {
            mAddTaskCB?.let { it(mTask!!) }
            dismiss()
        }

        return this
    }

    private fun clearCheckIcon() {
        icl_color_green.findViewById<View>(R.id.iv_check_mark).visibility = View.GONE
        icl_color_pink.findViewById<View>(R.id.iv_check_mark).visibility = View.GONE
        icl_color_blue.findViewById<View>(R.id.iv_check_mark).visibility = View.GONE
        icl_color_yellow.findViewById<View>(R.id.iv_check_mark).visibility = View.GONE
        icl_color_red.findViewById<View>(R.id.iv_check_mark).visibility = View.GONE
    }

    fun modifyTask(task: TaskItem): TaskEditDialog {
        mTask = task
        return this
    }

    fun addTaskCallBack(addTaskCB: (task: TaskItem)->Unit): TaskEditDialog {
        mAddTaskCB = addTaskCB
        return this
    }

    fun modifyTaskCallBack(modifyTaskCB: (task: TaskItem)->Unit): TaskEditDialog {
        mModifyTaskCB = modifyTaskCB
        return this
    }

    fun deleteTaskCallBack (delTaskCB: (task: TaskItem)->Unit): TaskEditDialog {
        mDelTaskCB = delTaskCB
        return this
    }
}