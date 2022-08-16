package com.threedust.app.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.view.SimpleDraweeView
import com.threedust.app.R
import com.threedust.app.base.BaseFragment
import com.threedust.app.model.entity.Gift
import com.threedust.app.ui.activity.GiftDetailActivity
import com.threedust.app.ui.widget.recyclerview.ViewHolder
import com.threedust.app.ui.widget.recyclerview.adapter.CommonAdapter
import com.threedust.app.ui.widget.recyclerview.adapter.OnItemClickListener
import com.threedust.app.utils.Logger
import com.threedust.app.utils.UIUtils
import kotlinx.android.synthetic.main.fragment_rv.*

class TreeFragment : BaseFragment() {

    var mGiftItemAdapter: RVTreeAdapter? = null

    companion object {
        fun getInstance(): TreeFragment {
            return TreeFragment()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_rv

    override fun initView() {
        val giftArr = arrayListOf<Gift>(
            Gift(
                1,
                "Tomato tree",
                introduce = "The tomato is the edible berry of the plant Solanum lycopersium, commonly known as the tomato plant.",
                rarity = 7,
                cur_growth = 39,
                total_growth = 60,
                res_id = R.mipmap.tree
            ),
            Gift(
                2,
                "Tomato tree",
                introduce = "The tomato is the edible berry of the plant Solanum lycopersium, commonly known as the tomato plant.",
                rarity = 7,
                cur_growth = 50,
                total_growth = 60,
                res_id = R.mipmap.tree
            ),
            Gift(
                3,
                "Tomato tree",
                introduce = "The tomato is the edible berry of the plant Solanum lycopersium, commonly known as the tomato plant.",
                rarity = 7,
                cur_growth = 0,
                total_growth = 60,
                res_id = R.mipmap.tree
            )
        )

        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mGiftItemAdapter = RVTreeAdapter(requireContext(), giftArr)
        rv_content.adapter = mGiftItemAdapter
        rv_content.layoutManager = linearLayoutManager

        mGiftItemAdapter?.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Any?, position: Int) {
                GiftDetailActivity.gift = obj as Gift?
                startActivity(Intent(requireContext(), GiftDetailActivity::class.java))
            }
        })
    }

    override fun lazyLoad() {

    }

    class RVTreeAdapter(ctx: Context, dataList: ArrayList<Gift>) :
        CommonAdapter<Gift>(ctx, dataList, R.layout.rv_item_tree) {

        override fun bindData(holder: ViewHolder, data: Gift, position: Int) {
            with(holder) {
                val tv_gift_name = getView<TextView>(R.id.tv_gift_name)
                tv_gift_name.text = data.gift_name
                val imgView = getView<SimpleDraweeView>(R.id.iv_gift_pic)
                imgView.setImageResource(R.mipmap.tree)
                val textView = getView<TextView>(R.id.tv_growth)
                textView.text = data.cur_growth.toString() + "/" + data.total_growth.toString()
                val seekBar = getView<SeekBar>(R.id.sb_growth)
                seekBar.progress = data.cur_growth * 100 / data.total_growth
                seekBar.isEnabled = false
            }
        }
    }

}