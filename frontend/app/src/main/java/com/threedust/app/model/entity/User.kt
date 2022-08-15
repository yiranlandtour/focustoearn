package com.threedust.app.model.entity

data class User (
    val id: Long = -1, // id
    var wallet_id: String = "",
    var nick_name: String = "Default user",
    var coin_num: Float = 0f
)