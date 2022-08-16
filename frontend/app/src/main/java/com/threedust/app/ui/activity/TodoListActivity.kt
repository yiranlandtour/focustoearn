package com.threedust.app.ui.activity

import android.annotation.SuppressLint
import com.google.android.flexbox.*
import com.threedust.app.MyApp
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.model.entity.TaskItem
import com.threedust.app.ui.adapter.RVTaskNameAdapter
import com.threedust.app.ui.dialog.TaskEditDialog
import com.threedust.app.ui.widget.recyclerview.adapter.OnItemClickListener
import com.threedust.app.utils.ConfigUtils
import com.threedust.app.utils.Logger
import com.threedust.app.utils.SysUtils
import kotlinx.android.synthetic.main.activity_todo_list.*
import okhttp3.internal.concurrent.Task

class TodoListActivity : BaseActivity() {

    lateinit var mTaskNameItemAdapter: RVTaskNameAdapter

    override fun layoutId(): Int = R.layout.activity_todo_list

    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {

        iv_back.setOnClickListener { finish() }

        btn_add.setOnClickListener {
            TaskEditDialog(this)
                .addTaskCallBack {
                    MyApp.appConf.task_list.add(it)
                    ConfigUtils.storeTaskList(MyApp.appConf.task_list)
                    mTaskNameItemAdapter.notifyDataSetChanged()
                }.build().show()
        }

        mTaskNameItemAdapter = RVTaskNameAdapter(this, MyApp.appConf.task_list)
        val flexBoxLM = FlexboxLayoutManager(this)
        flexBoxLM.flexWrap = FlexWrap.WRAP
        flexBoxLM.flexDirection = FlexDirection.ROW
        flexBoxLM.alignItems = AlignItems.FLEX_START
        flexBoxLM.justifyContent = JustifyContent.FLEX_START

        rv_task_name.layoutManager = flexBoxLM
        rv_task_name.adapter = mTaskNameItemAdapter

        mTaskNameItemAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Any?, position: Int) {
                TaskEditDialog(this@TodoListActivity)
                    .modifyTask(obj as TaskItem)
                    .modifyTaskCallBack {
                        for (i in 0 until MyApp.appConf.task_list.size) {
                            if (it.id == MyApp.appConf.task_list[i].id) {
                                MyApp.appConf.task_list[i] = it
                                ConfigUtils.storeTaskList(MyApp.appConf.task_list)
                                break
                            }
                        }
                        mTaskNameItemAdapter.notifyDataSetChanged()
                    }.deleteTaskCallBack {
                        if (MyApp.appConf.task_list.size == 1) {
                            SysUtils.showToast("There must be at least one Task.")
                        } else {
                            for (i in 0 until MyApp.appConf.task_list.size) {
                                if (it.id == MyApp.appConf.task_list[i].id) {
                                    MyApp.appConf.task_list.remove(it)
                                    ConfigUtils.storeTaskList(MyApp.appConf.task_list)
                                    break
                                }
                            }
                            mTaskNameItemAdapter.notifyDataSetChanged()
                        }
                    }.build().show()
            }
        })
    }

    override fun initData() {

    }

    override fun retryRequest() {

    }

}