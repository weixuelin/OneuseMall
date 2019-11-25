package com.qyc.wxl.oneusemall.ui.user.activity

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Message
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import com.qyc.wxl.oneusemall.base.Share
import com.qyc.wxl.oneusemall.ui.login.LoginActivity
import com.wt.weiutils.time.DatePickDialog
import com.wt.weiutils.time.bean.DateType
import com.wt.weiutils.utils.AppManager
import com.wt.weiutils.weight.CustomPop
import com.yanzhenjie.alertdialog.AlertDialog
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.dialog_edit_sex.*
import java.text.SimpleDateFormat

/**
 * 个人资料
 */
class EditInfoActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
    }

    override fun setContentView(): Int {
        return R.layout.activity_edit
    }

    override fun initData() {
        titleBar!!.title = "个人资料"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
    }

    override fun initListener() {
        relative_edit_sex.setOnClickListener {
            dialog_sex(0)
        }
        relative_edit_head.setOnClickListener {
            dialog_sex(1)
        }
        relative_edit_safe.setOnClickListener {
            //账户设置
            startActivity(Intent(this, SettingActivity::class.java))
        }
        relative_edit_birthday.setOnClickListener {
            val dialog = DatePickDialog(this)
            //设置上下年分限制
            dialog.setYearLimt(200)
            //设置标题
            dialog.setTitle("选择日期")
            //设置类型
            dialog.setType(DateType.TYPE_YMD)
            //设置消息体的显示格式，日期格式
            dialog.setMessageFormat("yyyy-MM-dd")
            //设置选择回调
            dialog.setOnChangeLisener(null)
            //设置点击确定按钮回调
            dialog.show()
            dialog.setOnSureLisener { date ->
                val time = SimpleDateFormat("yyyy-MM-dd").format(date)
                dialog.dismiss()
                text_edit_birthday.text = time
            }
        }
        btn_edit_out.setOnClickListener {
            //退出登录
            outLogin()
        }
    }

    private fun outLogin() {
        AlertDialog.newBuilder(context)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("确定退出当前账户？")
                .setPositiveButton("确定") { dialog, which ->
                    dialog.dismiss()
                    Share.clearUidOrToken(this)
                    startActivity(Intent(context, LoginActivity::class.java))
                    finish()
                    AppManager.getInstance().finishAllActivity()
                }
                .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                .show()
    }

    private var dialog_tips: Dialog? = null
    private fun dialog_sex(type: Int) {
        val inflater_type = layoutInflater
        val layout_type = inflater_type.inflate(R.layout.dialog_edit_sex, null)
        val record_type = android.app.AlertDialog.Builder(this)
        dialog_tips = record_type.create()

        dialog_tips!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)//设置Dialog背景透明效果
        dialog_tips!!.show()
        dialog_tips!!.setContentView(layout_type)
        dialog_tips!!.setCanceledOnTouchOutside(true)//
        if (type == 0) {
            dialog_tips!!.text_dialog_title.text = "选择性别"
            dialog_tips!!.text_dialog_woman.text = "女"
            dialog_tips!!.text_dialog_man.text = "男"
            dialog_tips!!.image_dialog_man.setImageResource(R.drawable.man)
            dialog_tips!!.image_dialog_woman.setImageResource(R.drawable.woman)
        } else {
            dialog_tips!!.text_dialog_title.text = "选择头像"
            dialog_tips!!.text_dialog_man.text = "拍照"
            dialog_tips!!.text_dialog_woman.text = "相册"
            dialog_tips!!.image_dialog_man.setImageResource(R.drawable.photo)
            dialog_tips!!.image_dialog_woman.setImageResource(R.drawable.image)
        }
        dialog_tips!!.linear_edit_man.setOnClickListener {
            if (type == 1)
                if (checkCameraPremission()) {
                    PictureSelector.create(this)
                            .openCamera(PictureMimeType.ofImage())
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                } else {
                    ActivityCompat.requestPermissions(this,
                            arrayOf(android.Manifest.permission.CAMERA,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE), CustomPop.CAMERA_PERMISSION_CODE)

                }
            dialog_tips!!.dismiss()
        }
        dialog_tips!!.linear_edit_woman.setOnClickListener {
            if (type == 1)
                if (checkPhotoPremission()) {
                    PictureSelector.create(this)
                            .openGallery(PictureMimeType.ofImage())
                            .minSelectNum(1)
                            .previewImage(false)// 是否可预览图片
                            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                            .isCamera(true)// 是否显示拍照按钮
                            .enableCrop(false)// 是否裁剪
                            .compress(true)// 是否压缩
                            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                            .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                            .rotateEnabled(false) // 裁剪是否可旋转图片
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                } else {
                    ActivityCompat.requestPermissions(this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE), CustomPop.PHOTO_PERMISSION_CODE)
                }
            dialog_tips!!.dismiss()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CustomPop.CAMERA_PERMISSION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    PictureSelector.create(this)
                            .openCamera(PictureMimeType.ofImage())
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                }
            }
            CustomPop.PHOTO_PERMISSION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    PictureSelector.create(this)
                            .openGallery(PictureMimeType.ofImage())
                            .minSelectNum(1)
                            .previewImage(false)// 是否可预览图片
                            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                            .isCamera(true)// 是否显示拍照按钮
                            .enableCrop(false)// 是否裁剪
                            .compress(true)// 是否压缩
                            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                            .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                            .rotateEnabled(false) // 裁剪是否可旋转图片
                            .forResult(PictureConfig.CHOOSE_REQUEST)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val images: List<LocalMedia>
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调

                images = PictureSelector.obtainMultipleResult(data)
                Log.i("toby", "path---------->" + images.size)
//                HttpUtil.getInstance().postImageOneNoProgress(Config.UPLOAD_IMAGE_URL, images[0].path, Config.UPLOAD_CODE, handler)
//                loadingDialog!!.show()
            }
        }
    }
}