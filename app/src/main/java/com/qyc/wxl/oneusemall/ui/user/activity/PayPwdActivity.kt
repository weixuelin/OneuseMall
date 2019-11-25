package com.qyc.wxl.oneusemall.ui.user.activity

import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import kotlinx.android.synthetic.main.activity_pay_pwd.*

/**
 * 修改支付密码
 */
class PayPwdActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
        text_account.addTextChangedListener(mTextWatcher)
        text_password.addTextChangedListener(mTextWatcher)
        text_code.addTextChangedListener(mTextWatcher)
        text_password_ag.addTextChangedListener(mTextWatcher)
    }

    override fun setContentView(): Int {
        return R.layout.activity_pay_pwd
    }

    override fun initData() {
        titleBar!!.title = "修改支付密码"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
    }

    override fun initListener() {

    }
    private val mTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            var account = text_account.text.toString()
            var password = text_password.text.toString()
            var password_ag = text_password_ag.text.toString()
            var code = text_code.text.toString()
            if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(code)&& !TextUtils.isEmpty(password_ag)) {
                btn_register.setBackgroundResource(R.drawable.btn_red_circle)
                btn_register.setTextColor(ContextCompat.getColor(this@PayPwdActivity,R.color.white))
                btn_register.isEnabled = true
            } else {
                btn_register.setBackgroundResource(R.drawable.btn_grey_circle)
                btn_register.setTextColor(ContextCompat.getColor(this@PayPwdActivity,R.color.grey_hint))
                btn_register.isEnabled = false
            }
        }
    }
}