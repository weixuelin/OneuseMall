package com.qyc.wxl.oneusemall.ui.user.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProV4Fragment
import com.qyc.wxl.oneusemall.ui.user.activity.*
import com.wt.weiutils.utils.ValuesUtils
import kotlinx.android.synthetic.main.fragment_user.*
import q.rorbin.badgeview.QBadgeView


class UserFragment : ProV4Fragment() {

    override fun handler(msg: Message) {

    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        isPrepared = true
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        text_user_share.setOnClickListener {
            //邀请赚现金
            startActivity(Intent(activity, ShareActivity::class.java))
        }
        image_my_setting.setOnClickListener {
            //账户设置
            startActivity(Intent(activity, SettingActivity::class.java))
        }
        linear_my_edit.setOnClickListener {
            //个人资料
            startActivity(Intent(activity, EditInfoActivity::class.java))
        }
        image_my_message.setOnClickListener {
            //我的消息
            startActivity(Intent(activity, MessageActivity::class.java))
        }
        relative_user_address.setOnClickListener {
            //地址管理
            val intent = Intent(activity, AddressActivity::class.java)
            intent.putExtra("status", "1")
            startActivity(intent)
        }
        relative_user_kefu.setOnClickListener {
            //联系客服
            startActivity(Intent(activity, KefuActivity::class.java))
        }
        relative_user_team.setOnClickListener {
            //我的团队
            startActivity(Intent(activity, TeamActivity::class.java))
        }
        relative_user_collection.setOnClickListener {
            //我的收藏
            startActivity(Intent(activity, CollectionActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        showNofaBadge_Left(1)
    }

    override fun loadData() {
        if (!isPrepared || !isVisable) {
            return
        } else {
            log("个人中心可见")
        }
    }

    private var myMessageBadge: QBadgeView? = null
    fun showNofaBadge_Left(count: Int) {
        if (myMessageBadge == null && count > 0) {
            myMessageBadge = QBadgeView(context)
            myMessageBadge!!.bindTarget(linear_no_fa)
                    .setGravityOffset(0F,
                            ValuesUtils.getDimen(context, R.dimen.qb_px_15).toFloat(), false)
                    .setBadgeTextSize(ValuesUtils.getDimen(context, R.dimen.qb_px_20).toFloat(), false)
                    .setBadgeBackgroundColor(Color.parseColor("#FF8F2D"))
                    .setBadgeTextColor(Color.parseColor("#ffffff"))
                    .setBadgeGravity(Gravity.END or Gravity.TOP)
                    .badgeNumber = 1
        }
    }
}