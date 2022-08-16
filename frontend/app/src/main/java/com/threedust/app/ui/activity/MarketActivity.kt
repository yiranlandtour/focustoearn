package com.threedust.app.ui.activity

import android.content.Context
import android.content.Intent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.facebook.drawee.view.SimpleDraweeView
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.model.entity.Gift
import com.threedust.app.ui.widget.recyclerview.ViewHolder
import com.threedust.app.ui.widget.recyclerview.adapter.CommonAdapter
import com.threedust.app.ui.widget.recyclerview.adapter.OnItemClickListener
import com.threedust.app.utils.Logger
import com.threedust.app.utils.UIUtils
import kotlinx.android.synthetic.main.activity_market.*

class MarketActivity : BaseActivity() {

    lateinit var mGiftItemAdapter: RVGiftNameAdapter

    override fun layoutId(): Int = R.layout.activity_market

    override fun initView() {

        iv_back.setOnClickListener { finish() }

        val giftArr = arrayListOf<Gift>(
            Gift(
                1,
                "Tomato plants",
                1.2f,
                introduce = "The tomato is the edible berry of the plant Solanum lycopersium, commonly known as the tomato plant.",
                rarity = 7,
                res_id = R.mipmap.tree,
                can_buy = true
            ),
            Gift(
                2,
                "Seeds",
                1.2f,
                introduce = "The tomato is the edible berry of the plant Solanum lycopersium, commonly known as the tomato plant.",
                rarity = 7,
                res_id = R.mipmap.seed,
                can_buy = true
            ),
            Gift(
                3,
                "Tomato plants",
                1.2f,
                introduce = "The tomato is the edible berry of the plant Solanum lycopersium, commonly known as the tomato plant.",
                rarity = 7,
                res_id = R.mipmap.tree,
                can_buy = true
            ),
            Gift(
                4,
                "Seeds",
                1.2f,
                introduce = "The tomato is the edible berry of the plant Solanum lycopersium, commonly known as the tomato plant.",
                rarity = 7,
                res_id = R.mipmap.seed,
                can_buy = true
            ),
            Gift(
                5,
                "Tomato plants",
                1.2f,
                introduce = "The tomato is the edible berry of the plant Solanum lycopersium, commonly known as the tomato plant.",
                rarity = 7,
                res_id = R.mipmap.tree,
                can_buy = true
            ),
            Gift(
                5,
                "Seeds",
                1.2f,
                introduce = "The tomato is the edible berry of the plant Solanum lycopersium, commonly known as the tomato plant.",
                rarity = 7,
                res_id = R.mipmap.seed,
                can_buy = true
            )
        )

        mGiftItemAdapter = RVGiftNameAdapter(this, giftArr)
        val gridLM = GridLayoutManager(this, 2)
        rv_gift.layoutManager = gridLM
        rv_gift.adapter = mGiftItemAdapter

        mGiftItemAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Any?, position: Int) {
                GiftDetailActivity.gift = obj as Gift?
                startActivity(Intent(this@MarketActivity, GiftDetailActivity::class.java))
            }
        })

    }

    override fun initData() {

    }

    override fun retryRequest() {

    }

    class RVGiftNameAdapter(ctx: Context, dataList: ArrayList<Gift>) :
        CommonAdapter<Gift>(ctx, dataList, R.layout.rv_item_gift) {

        override fun bindData(holder: ViewHolder, data: Gift, position: Int) {
            with(holder) {
                val tv_gift_name = getView<TextView>(R.id.tv_gift_name)
                tv_gift_name.text = data.gift_name
                val imgView = getView<SimpleDraweeView>(R.id.iv_gift_pic)
                val llImg = getView<LinearLayout>(R.id.ll_gift_img_wrap)
                val hw = UIUtils.screenWidthPx / 2 - UIUtils.dp2px(36f)
                llImg.layoutParams.height = hw
                llImg.layoutParams.width = hw

                imgView.setImageResource(data.res_id)
            }
        }
    }
}