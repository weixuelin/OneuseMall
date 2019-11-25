package com.qyc.wxl.oneusemall.ui.login

import android.content.Intent
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import com.qyc.wxl.oneusemall.ui.main.activity.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录
 */
class LoginActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
        setTranslucentForImageView(true, linear_all)
        text_account.addTextChangedListener(mTextWatcher)
        text_password.addTextChangedListener(mTextWatcher)

    }

    override fun setContentView(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

    }

    override fun initListener() {
        text_register.setOnClickListener {
            //注册账号
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        text_forget.setOnClickListener {
            //找回密码
            var intent = Intent(this, FindPwdActivity::class.java)
            intent.putExtra("status","1")
            startActivity(intent)
        }
        linear_wei.setOnClickListener {
            //微信登录后绑定账号
            startActivity(Intent(this, BangActivity::class.java))
        }
        btn_login.setOnClickListener {
            //登录
            startActivity(Intent(this, MainActivity::class.java))
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
                btn_login.setBackgroundResource(R.drawable.btn_red_circle)
                btn_login.setTextColor(ContextCompat.getColor(this@LoginActivity,R.color.white))
                btn_login.isEnabled = true
            } else {
                btn_login.setBackgroundResource(R.drawable.btn_grey_circle)
                btn_login.setTextColor(ContextCompat.getColor(this@LoginActivity,R.color.grey_hint))
                btn_login.isEnabled = false
            }
        }
    }


}