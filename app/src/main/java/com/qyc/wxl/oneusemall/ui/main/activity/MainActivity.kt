package com.qyc.wxl.oneusemall.ui.main.activity

import android.annotation.SuppressLint
import android.os.Message
import android.os.Process
import android.view.KeyEvent
import com.jaeger.library.StatusBarUtil
import com.qyc.wxl.oneusemall.Apps
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import com.qyc.wxl.oneusemall.ui.main.adapter.MainPagerAdapter
import com.wt.weiutils.utils.AppManager
import com.wt.weiutils.utils.ValuesUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ProActivity() {
    private var adapter: MainPagerAdapter? = null

    override fun setContentView(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

        adapter = MainPagerAdapter(supportFragmentManager, "")
        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = 0
        viewpager.currentItem = 0
        bottom_main.isChecked = true
        radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.bottom_main -> viewpager.setCurrentItem(0, false)

                R.id.bottom_classify -> viewpager.setCurrentItem(1, false)

                R.id.bottom_consult -> viewpager.setCurrentItem(2, false)

                R.id.bottom_car ->
                    viewpager.setCurrentItem(3, false)
                R.id.bottom_person ->
                    viewpager.setCurrentItem(4, false)
            }
        }
    }

    override fun initData() {
        StatusBarUtil.setLightMode(this)
    }

    override fun initListener() {

    }

    override fun handler(msg: Message) {

    }

    private var exitTime: Long = 0
    @SuppressLint("WrongConstant")
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToastShort(ValuesUtils.getString(this, R.string.exit) + applicationInfo.loadLabel(
                        packageManager))
                exitTime = System.currentTimeMillis()
            } else {
                this.finish()
                AppManager.getInstance().finishAllActivity()
                Apps.getApps().onTerminate()
                Process.killProcess(Process.myPid())
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
