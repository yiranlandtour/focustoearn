package com.threedust.app.ui.activity

import android.content.Intent
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.threedust.app.R
import com.threedust.app.base.BaseActivity
import com.threedust.app.ui.dialog.BeautyDialog
import com.threedust.app.ui.fragment.DecorationFragment
import com.threedust.app.ui.fragment.OthersFragment
import com.threedust.app.ui.fragment.TreeFragment
import com.threedust.app.utils.ConfigUtils
import kotlinx.android.synthetic.main.activity_land.*
import kotlinx.android.synthetic.main.activity_market.*
import kotlinx.android.synthetic.main.activity_market.iv_back

class LandActivity : BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_land

    val mLandPrice = 2f
    private var mTreeFragment: TreeFragment? = null
    private var mDecorationFragment: DecorationFragment? = null
    private var mOthersFragment: OthersFragment? = null

    override fun initView() {
        iv_back.setOnClickListener { finish() }

        if (!ConfigUtils.isHaveLand()) {
            iv_gift_pic.setImageResource(R.mipmap.land_1)
        } else if (!ConfigUtils.isHaveTree()) {
            iv_gift_pic.setImageResource(R.mipmap.tree)
            tv_rarity.text = "for free"
            tv_desc.text = "You can plant this tree, but it cannot be transferred."
        } else {
            iv_gift_pic.setImageResource(R.mipmap.land_1)
            ll_btn.visibility = View.GONE
            tv_price_title.text = "Focus value:"
            tv_rarity.text = ConfigUtils.getUserCoinCount().toString()
            tv_desc.visibility = View.GONE
            clearRadio()
            icl_tree.findViewById<View>(R.id.iv_solid_color).visibility = View.VISIBLE
            initFragment()
        }

        btn_mint_now.setOnClickListener {
            // have not land
            if (!ConfigUtils.isHaveLand()) {
                if (ConfigUtils.getUserCoinCount() < mLandPrice) {
                    BeautyDialog(this).title("Your coin is not enough for land, go to earn more coins.")
                        .headImage(R.mipmap.bell).okBtnClickListener {
                            this.finish()
                        }.show()
                } else {
                    ConfigUtils.recordBuyLand()
                    ConfigUtils.addUserCoinCount(-mLandPrice)
                    BeautyDialog(this).title("Congratulations, You can mint one tree and start planting.")
                        .headImage(R.mipmap.bell).okBtnClickListener {
                            startActivity(Intent(this, LandActivity::class.java))
                            this.finish()
                        }.show()
                }
            }
            // have land, not have tree
            else if (!ConfigUtils.isHaveTree()) {
                ConfigUtils.recordBuyTree()
                BeautyDialog(this).title("Congratulations, You have your own tomato tree.")
                    .headImage(R.mipmap.bell).okBtnClickListener {
                        startActivity(Intent(this, LandActivity::class.java))
                        this.finish()
                    }.show()
            } else {
                ll_btn.visibility = View.GONE
            }
        }
    }

    override fun initData() {

    }

    override fun retryRequest() {

    }

    private fun clearRadio() {
        icl_tree.findViewById<View>(R.id.iv_solid_color).visibility = View.GONE
        icl_decoration.findViewById<View>(R.id.iv_solid_color).visibility = View.GONE
        icl_others.findViewById<View>(R.id.iv_solid_color).visibility = View.GONE
    }

    private fun initFragment() {
        switchFragment(0)
        ll_tree.setOnClickListener {
            clearRadio()
            icl_tree.findViewById<View>(R.id.iv_solid_color).visibility = View.VISIBLE
            switchFragment(0)
        }
        ll_decoration.setOnClickListener {
            clearRadio()
            icl_decoration.findViewById<View>(R.id.iv_solid_color).visibility = View.VISIBLE
            switchFragment(1)
        }
        ll_others.setOnClickListener {
            clearRadio()
            icl_others.findViewById<View>(R.id.iv_solid_color).visibility = View.VISIBLE
            switchFragment(2)
        }
    }

    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> mTreeFragment?.let {
                transaction.show(it)
            } ?: TreeFragment.getInstance().let {
                mTreeFragment = it
                transaction.add(R.id.fl_content, it, "tree")
            }
            1 -> mDecorationFragment?.let {
                transaction.show(it)
            } ?: DecorationFragment.getInstance().let {
                mDecorationFragment = it
                transaction.add(R.id.fl_content, it, "decoration")
            }
            2 -> mOthersFragment?.let {
                transaction.show(it)
            } ?: OthersFragment.getInstance().let {
                mOthersFragment = it
                transaction.add(R.id.fl_content, it, "others")
            }
            else -> { }
        }
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        mTreeFragment?.let { transaction.hide(it) }
        mDecorationFragment?.let { transaction.hide(it) }
        mOthersFragment?.let { transaction.hide(it) }
    }

}