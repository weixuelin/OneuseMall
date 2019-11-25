package com.qyc.wxl.oneusemall.ui.user.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Message
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import kotlinx.android.synthetic.main.activity_kefu.*

/**
 * 联系客服
 */
class KefuActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
    }

    override fun setContentView(): Int {
        return R.layout.activity_kefu
    }

    override fun initData() {
        titleBar!!.title = "联系客服"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
    }

    override fun initListener() {
        relative_kefu_phone.setOnClickListener {
            phoneCall()
        }
        text_feedback.setOnClickListener {
            //意见反馈
            var intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }
    }

    private fun phoneCall() {
        val code = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)

        if (code == PackageManager.PERMISSION_GRANTED) {

            phoneCallDialog()

        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 234)

        }
    }

    var phone = "15865854858"
    @SuppressLint("MissingPermission")
    private fun phoneCallDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("是否拨打电话?").setPositiveButton("拨打") { dialog, _ ->
            run {
                val intent = Intent(Intent.ACTION_CALL)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.data = Uri.parse("tel:$phone")
                startActivity(intent)
                dialog.dismiss()

            }
        }.setNegativeButton("取消") { _, _ -> }.show()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 234) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                phoneCallDialog()
            }
        }

    }


}