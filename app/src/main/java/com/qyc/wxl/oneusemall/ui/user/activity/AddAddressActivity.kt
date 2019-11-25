package com.qyc.wxl.oneusemall.ui.user.activity

import android.os.Message
import androidx.core.content.ContextCompat
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity

/**
 * 添加/编辑地址
 */
class AddAddressActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
    }

    override fun setContentView(): Int {
        return R.layout.activity_add_address
    }

    override fun initData() {
        titleBar!!.title = "收货地址"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
    }

    override fun initListener() {

    }
}