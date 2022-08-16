package com.threedust.app.ui.fragment

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.drawee.view.SimpleDraweeView
import com.threedust.app.R
import com.threedust.app.base.BaseFragment
import com.threedust.app.model.entity.Gift
import com.threedust.app.ui.widget.recyclerview.ViewHolder
import com.threedust.app.ui.widget.recyclerview.adapter.CommonAdapter
import kotlinx.android.synthetic.main.fragment_rv.*

class OthersFragment : BaseFragment() {

    companion object {
        fun getInstance(): OthersFragment {
            return OthersFragment()
        }
    }
    var mGiftItemAdapter : RVOthersAdapter? = null
    override fun layoutId(): Int = R.layout.fragment_rv

    override fun initView() {
        val giftArr = arrayListOf (
            Gift(
                1,
                "others",
            )
        )

        val linearLayoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mGiftItemAdapter = RVOthersAdapter(requireContext(), giftArr)
        rv_content.adapter = mGiftItemAdapter
        rv_content.layoutManager = linearLayoutManager

        rv_content.visibility = View.GONE
        ll_empty.visibility = View.VISIBLE
    }

    override fun lazyLoad() {

    }

    class RVOthersAdapter(ctx: Context, dataList: ArrayList<Gift>) :
        CommonAdapter<Gift>(ctx, dataList, R.layout.rv_item_decorations) {

        override fun bindData(holder: ViewHolder, data: Gift, position: Int) {
            with(holder) {
                val tv_gift_name = getView<TextView>(R.id.tv_gift_name)
                tv_gift_name.text = data.gift_name
                val imgView = getView<SimpleDraweeView>(R.id.iv_gift_pic)
                imgView.setImageResource(R.mipmap.ring)
            }
        }
    }

}