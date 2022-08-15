package com.threedust.app.model.entity

data class AppConf (
    var task_list: ArrayList<TaskItem> = arrayListOf (
        TaskItem(1, "Study", "green"),
        TaskItem(2, "Play", "pink"),
        TaskItem(3, "Work", "blue"),
        TaskItem(4, "Football", "yellow"),
        TaskItem(5, "Sleep", "red")),
    val user_policy_url: String = "http://app.xuyaomaoxian.com:8180/focuson_html/user_policy.html",
    val privacy_policy_url: String = "http://app.xuyaomaoxian.com:8180/focuson_html/privacy_policy.html"
)