package com.qyc.wxl.oneusemall.ui.login

import android.content.Intent
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import kotlinx.android.synthetic.main.activity_bang.*

/**
 * 绑定账号
 */
class BangActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
        setTranslucentForImageView(true, linear_all)
        text_account.addTextChangedListener(mTextWatcher)
        text_password.addTextChangedListener(mTextWatcher)

    }

    override fun setContentView(): Int {
        return R.layout.activity_bang
    }

    override fun initData() {

    }

    override fun initListener() {
        text_register.setOnClickListener {
            //注册账号
            startActivity(Intent(this, RegisterActivity::class.java))
        }
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
            if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
                btn_sure.setBackgroundResource(R.drawable.btn_red_circle)
                btn_sure.setTextColor(resources.getColor(R.color.white))
                btn_sure.isEnabled = true
            } else {
                btn_sure.setBackgroundResource(R.drawable.btn_grey_circle)
                btn_sure.setTextColor(resources.getColor(R.color.grey_hint))
                btn_sure.isEnabled = false
            }
        }
    }


}