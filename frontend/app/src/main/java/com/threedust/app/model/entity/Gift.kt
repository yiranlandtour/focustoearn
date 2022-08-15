package com.threedust.app.model.entity

data class Gift(
    var id: Int,
    var gift_name: String,
    var price: Float,
    var image_url: String,
    var introduce: String
)