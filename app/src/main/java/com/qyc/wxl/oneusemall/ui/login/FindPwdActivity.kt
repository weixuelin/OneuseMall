package com.qyc.wxl.oneusemall.ui.login

import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import kotlinx.android.synthetic.main.activity_find.*


/**
 * 找回密码
 */
class FindPwdActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    var status = ""
    override fun initView() {
        setTranslucentForImageView(true, linear_all)
        text_account.addTextChangedListener(mTextWatcher)
        text_password.addTextChangedListener(mTextWatcher)
        text_code.addTextChangedListener(mTextWatcher)
        status = intent.getStringExtra("status")
        when (status) {
            "1" -> {
                text_title.text = "找回密码"
                til_account.hint = "请输入手机号"
                til_password.hint = "请输入密码"
            }
            "2" -> {
                text_title.text = "修改密码"
                text_account.setText("138****6356")
                til_password.hint = "请设置新密码"
            }
            "3" -> {
                text_title.text = "绑定手机号"
                til_account.hint = "请输入手机号"
                til_password.visibility = View.GONE
                view_line.visibility = View.GONE
            }
        }

    }

    override fun setContentView(): Int {
        return R.layout.activity_find
    }

    override fun initData() {

    }

    override fun initListener() {
        image_close.setOnClickListener {
            finish()
        }
    }


    private val mTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            var account = text_account.text.toString()
            var password = text_password.text.toString()
            var code = text_code.text.toString()
            if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(code)) {
                btn_register.setBackgroundResource(R.drawable.btn_red_circle)
                btn_register.setTextColor(resources.getColor(R.color.white))
                btn_register.isEnabled = true
            } else {
                btn_register.setBackgroundResource(R.drawable.btn_grey_circle)
                btn_register.setTextColor(resources.getColor(R.color.grey_hint))
                btn_register.isEnabled = false
            }
        }
    }


}