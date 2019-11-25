package com.qyc.wxl.oneusemall.ui.user.activity

import android.app.Dialog
import android.os.Message
import androidx.core.content.ContextCompat
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.dialog_feedback.*

/**
 * 意见反馈
 */
class FeedbackActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
    }

    override fun setContentView(): Int {
        return R.layout.activity_feedback
    }

    override fun initData() {
        titleBar!!.title = "意见反馈"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
    }

    override fun initListener() {
        btn_feedback_submit.setOnClickListener {
            dialogTips()
        }
    }
    private var dialog_tips: Dialog? = null
    private fun dialogTips() {
        val inflater_type = layoutInflater
        val layout_type = inflater_type.inflate(R.layout.dialog_feedback, null)
        val record_type = android.app.AlertDialog.Builder(this)
        dialog_tips = record_type.create()

        dialog_tips!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)//设置Dialog背景透明效果
        dialog_tips!!.show()
        dialog_tips!!.setContentView(layout_type)
        dialog_tips!!.setCanceledOnTouchOutside(true)//
        dialog_tips!!.text_sure.setOnClickListener {

            dialog_tips!!.dismiss()
        }
    }

}