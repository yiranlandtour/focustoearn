package com.threedust.app.ui.fragment

import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.fragment_rv.*

class DecorationFragment : BaseFragment() {

    companion object {
        fun getInstance(): DecorationFragment {
            return DecorationFragment()
        }
    }


    var mGiftItemAdapter : RVDecrationAdapter? = null

    override fun layoutId(): Int = R.layout.fragment_rv

    override fun initView() {
        val giftArr = arrayListOf (
            Gift(
                1,
                "Tomato ring",
                introduce = "A very precious ring. You can sell it for coin",
                res_id = R.mipmap.ring,
                rarity = 7,
                can_sell = true,
                show_growth = false,
                show_price = false
            )
        )

        val linearLayoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mGiftItemAdapter = RVDecrationAdapter(requireContext(), giftArr)
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

    class RVDecrationAdapter(ctx: Context, dataList: ArrayList<Gift>) :
        CommonAdapter<Gift>(ctx, dataList, R.layout.rv_item_decorations) {

        override fun bindData(holder: ViewHolder, data: Gift, position: Int) {
            with(holder) {
                val tv_gift_name = getView<TextView>(R.id.tv_gift_name)
                tv_gift_name.text = data.gift_name
                val imgView = getView<SimpleDraweeView>(R.id.iv_gift_pic)
                imgView.setImageResource(data.res_id)
            }
        }
    }


}