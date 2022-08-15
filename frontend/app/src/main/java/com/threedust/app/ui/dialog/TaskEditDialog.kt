package com.threedust.app.ui.dialog

import android.content.Context
import com.threedust.app.R
import com.threedust.app.base.BaseDialog

class TaskEditDialog (context: Context) : BaseDialog(context) {

    init {
        setContentView(R.layout.dialog_task_edit)
    }
}