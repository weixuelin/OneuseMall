package com.qyc.wxl.oneusemall.ui.user.activity

import android.content.Intent
import android.os.Message
import androidx.core.content.ContextCompat
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import com.qyc.wxl.oneusemall.ui.login.FindPwdActivity
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * 账户设置
 */
class SettingActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
    }

    override fun setContentView(): Int {
        return R.layout.activity_setting
    }

    override fun initData() {
        titleBar!!.title = "账户设置"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
    }

    override fun initListener() {
        linear_setting_login.setOnClickListener {
            var intent = Intent(this, FindPwdActivity::class.java)
            intent.putExtra("status", "2")
            startActivity(intent)
        }
        linear_setting_phone.setOnClickListener {
            var intent = Intent(this, FindPwdActivity::class.java)
            intent.putExtra("status", "3")
            startActivity(intent)
        }
        linear_setting_pay.setOnClickListener {
            var intent = Intent(this, PayPwdActivity::class.java)
            startActivity(intent)
        }
    }
}