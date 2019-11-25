package com.qyc.wxl.oneusemall.ui.user.activity

import android.os.Message
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity

/**
 * 分享二维码
 */
class ShareActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
        setTranslucentForImageView(true, titleBar!!)

    }

    override fun setContentView(): Int {
        return R.layout.activity_share
    }

    override fun initData() {
        titleBar!!.title = "分享二维码"
    }

    override fun initListener() {
    }
}