package com.threedust.app.model.entity

data class TaskItem(
    var id: Int,
    var title: String,
    var color: String,
    var total_count: Int = 0,
    var total_time: Int = 0 // minutes
)