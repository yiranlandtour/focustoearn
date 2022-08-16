package com.threedust.app.ui.activity

import android.content.Intent
import android.view.View
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.model.entity.Gift
import com.threedust.app.ui.dialog.BeautyDialog
import com.threedust.app.ui.dialog.ConfirmDialog
import com.threedust.app.ui.dialog.SellDialog
import com.threedust.app.utils.ConfigUtils
import kotlinx.android.synthetic.main.activity_gift_detail.*
import kotlinx.android.synthetic.main.activity_market.*
import kotlinx.android.synthetic.main.activity_market.iv_back

class GiftDetailActivity : BaseActivity() {

    companion object {
        var gift: Gift? = null
    }

    override fun layoutId(): Int = R.layout.activity_gift_detail

    override fun initView() {
        iv_back.setOnClickListener { finish() }

        gift?.let { g ->
            iv_gift_pic.setImageResource(g.res_id)
            tv_gift_name.text = g.gift_name
            if (g.show_price) {
                tv_gift_price.text = g.price.toString() + " SOL"
            } else {
                tv_price_title.visibility = View.GONE
                tv_gift_price.visibility = View.GONE
            }
            tv_gift_introduce.text = g.introduce
            if (g.show_growth) {
                tv_growth.text = g.cur_growth.toString() + "/" + g.total_growth.toString()
                sb_growth.progress = g.cur_growth * 100 / g.total_growth
                sb_growth.isEnabled = false
            } else {
                rl_growth_title.visibility = View.GONE
                sb_growth.visibility = View.GONE
            }
            tv_rarity.text = g.rarity.toString() + "%"
            ll_buy_sell.visibility = View.VISIBLE
            if (g.can_buy) {
                btn_buy_sell.text = "Buy"
                btn_buy_sell.setOnClickListener {
                    ConfirmDialog(this).title("Buy now ?").okBtnClickListener {
                        if (ConfigUtils.getUserCoinCount() < g.price) {
                            BeautyDialog(this).title("Your coin is not enough for land, go to earn more coins.")
                                .headImage(R.mipmap.bell).show()
                        } else {
                            ConfigUtils.recordBuyLand()
                            ConfigUtils.addUserCoinCount(-g.price)
                            BeautyDialog(this).title("Congratulations, You got the gift.")
                                .headImage(R.mipmap.bell).okBtnClickListener {
                                    startActivity(Intent(this, LandActivity::class.java))
                                    this.finish()
                                }.show()
                        }
                    }.show()
                }
            } else if (g.can_sell) {
                btn_buy_sell.text = "Sell"
                btn_buy_sell.setOnClickListener { v ->
                    SellDialog(this).sellPrice(g.sell_price).okBtnClickListener { price ->
                        g.sell_price = price
                        g.is_selling = true
                        btn_buy_sell.text = "Selling"
                        btn_cancel.visibility = View.VISIBLE

                    }.show()
                }
                btn_cancel.setOnClickListener {
                    BeautyDialog(this).show2Btn().title("Are you sure to cancel the order?")
                        .headImage(R.mipmap.bell)
                        .okBtnClickListener {
                            g.sell_price = 0f
                            g.is_selling = false
                            btn_cancel.visibility = View.GONE
                            btn_buy_sell.text = "Sell"
                        }.show()
                }
            } else {
                ll_buy_sell.visibility = View.GONE
            }
        }
    }

    override fun initData() {

    }

    override fun retryRequest() {

    }


}