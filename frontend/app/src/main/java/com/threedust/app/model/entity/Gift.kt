package com.threedust.app.model.entity

data class Gift(
    var id: Int,
    var gift_name: String = "",
    var price: Float = 0f,
    var image_url: String = "",
    var introduce: String = "",
    var rarity: Int = 0,
    var cur_growth: Int = 0,
    var total_growth: Int = 60,
    var res_id: Int = 0,
    var can_buy: Boolean = false,
    var can_sell: Boolean = false,
    var show_growth: Boolean = true,
    var show_price: Boolean = true,
    var is_selling: Boolean = false,
    var sell_price: Float = 0f
)