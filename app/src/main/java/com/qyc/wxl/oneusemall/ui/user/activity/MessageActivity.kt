package com.qyc.wxl.oneusemall.ui.user.activity

import android.content.Intent
import android.graphics.Color
import android.os.Message
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import com.wt.weiutils.utils.ValuesUtils
import kotlinx.android.synthetic.main.activity_message.*
import q.rorbin.badgeview.QBadgeView

/**
 * 我的消息
 */
class MessageActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
        showNofaBadge_Left(5)

    }

    override fun setContentView(): Int {
        return R.layout.activity_message
    }

    override fun initData() {
        titleBar!!.title = "我的消息"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
    }

    override fun initListener() {
        relative_message_order.setOnClickListener {
            val intent = Intent(this,MessageMsgActivity::class.java)
            intent.putExtra("status","2")
            startActivity(intent)
        }
        relative_message_system.setOnClickListener {
            val intent = Intent(this,MessageMsgActivity::class.java)
            intent.putExtra("status","1")
            startActivity(intent)
        }
    }
    private var myMessageBadge: QBadgeView? = null
    fun showNofaBadge_Left(count: Int) {
        if (myMessageBadge == null && count > 0) {
            myMessageBadge = QBadgeView(context)
            myMessageBadge!!.bindTarget(linear_order)
                    .setGravityOffset(0F,
                            ValuesUtils.getDimen(context, R.dimen.qb_px_15).toFloat(), false)
                    .setBadgeTextSize(ValuesUtils.getDimen(context, R.dimen.qb_px_20).toFloat(), false)
                    .setBadgeBackgroundColor(Color.parseColor("#FF8F2D"))
                    .setBadgeTextColor(Color.parseColor("#ffffff"))
                    .setBadgeGravity(Gravity.END or Gravity.BOTTOM)
                    .badgeNumber = count
        }
    }
}