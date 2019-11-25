package com.qyc.wxl.oneusemall.base

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.*
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.jaeger.library.StatusBarUtil
import com.qyc.wxl.oneusemall.R
import com.wt.weiutils.utils.AppManager
import com.wt.weiutils.utils.CustomToast
import com.wt.weiutils.weight.LoadingDialog
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

abstract class ProActivity : AppCompatActivity(), OnTitleBarListener, View.OnClickListener {
    protected var loadingDialog: LoadingDialog? = null
    protected var savedInstanceStateMain: Bundle? = null
    protected var titleBar: TitleBar? = null
    protected var context: Context? = null
    var gson: Gson? = null

    private val ERROR = 404
    private val ERROR_BUG = 405
    var handler: Handler? = null
    var uid = 0
    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        loadingDialog = LoadingDialog(this, "")
        context = this
        this.savedInstanceStateMain = savedInstanceState
        setContentView(setContentView())
        setStatusBar()
        uid = Share.getUid(this)
        token = Share.getToken(this)
        gson = Gson()
        handler = ProHandler(this)
        titleBar = findViewById(R.id.titleBar)
        if (titleBar != null)
            titleBar!!.onTitleBarListener = this
        // 初始化ui
        initView()
        // 初始化数据
        initData()
        // 添加监听器
        initListener()
    }

    fun longToTime(l: Long, type: String): String {
        val format = SimpleDateFormat(type, Locale.CHINA)

        return format.format(Date(l * 1000))

    }

    val isDebug: Boolean = true

    fun log(message: String) {
        if (isDebug) {
            Log.i("toby", message)
        }

    }
    protected fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.titlebar_color), 0)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.black), 68)
    }

    fun setAlpha(f: Float) {
        val manager = window.attributes
        manager.alpha = f
        window.attributes = manager
    }

    fun setStatusBarColorChild(view: View) {
        StatusBarUtil.setTranslucentForImageView(this, 0, view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            val p = view.parent as View
            p?.setBackgroundColor(Color.parseColor("#33000000"))
        }
    }

    protected fun setTranslucentForImageView(isTranslucent: Boolean, view: View) {
        if (isTranslucent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarUtil.setTranslucentForImageView(this, 0, view)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else
                StatusBarUtil.setTranslucentForImageView(this, 84, view)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.titlebar_color), 0)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else
                StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorMain), 84)
        }
    }

    protected abstract fun setContentView(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    protected abstract fun initListener()

    protected fun hideIMM() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    override fun onClick(v: View) {}

    override fun onLeftClick(v: View) {
        finish()
    }

    override fun onTitleClick(v: View) {}

    override fun onRightClick(v: View) {}

    override fun onDestroy() {
        AppManager.getInstance().finishActivity(this)
        super.onDestroy()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            hideSoftInput(v, ev)

            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }

    private fun hideSoftInput(v: View?, ev: MotionEvent) {
        var v = v
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null && imm.isActive) {
            if (isShouldHideInput(v, ev)) {
                if (v == null) {
                    v = window.decorView
                }
                imm.hideSoftInputFromWindow(v!!.windowToken, 0)
            }
        }
    }

    /**
     * 判断是否要隐藏软键盘。
     * 除了EditText外，点击了其他地方，都要隐藏。
     *
     * @param v
     * @param event
     * @return
     */
    fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        return if (v is EditText) {
            !isPointInView(v, event.x.toInt(), event.y.toInt())
        } else true
    }
    /**
     * 检查数据存储权限
     */
    fun checkPhotoPremission(): Boolean {
        val code = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        return code == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 检查照相机权限
     */
    fun checkCameraPremission(): Boolean {
        val code = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        return code == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 判断位置(x, y)是否在view的显示范围内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    fun isPointInView(view: View, x: Int, y: Int): Boolean {
        val location = IntArray(2)
        // 点击事件获取的坐标为相对于window（如果activity是全屏的，Screen和Window一样）
        //        view.getLocationOnScreen(location);
        view.getLocationInWindow(location)
        val left = location[0]
        val top = location[1]
        val right = left + view.measuredWidth
        val bottom = top + view.measuredHeight
        return (y >= top && y <= bottom
                && x >= left && x <= right)
    }

    protected fun openActivity(cls: Class<*>) {
        startActivity(Intent(this, cls))
    }

    protected fun openActivity(cls: Class<*>, intentName: String, intentValue: String) {
        startActivity(Intent(this, cls).putExtra(intentName, intentValue))
    }

    companion object {

        class ProHandler(proActivity: ProActivity) : Handler(Looper.getMainLooper()) {
            val weak: WeakReference<ProActivity> = WeakReference(proActivity)
            val a = weak.get()
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when (msg!!.what) {

                    a!!.ERROR -> {
                        a.loadingDialog!!.dismiss()
                        a.showToastShort("网络连接超时，请检查网络!")
                    }

                    a.ERROR_BUG -> {
                        a.loadingDialog!!.dismiss()
                        a.showToastShort("提交失败，请稍后再试")
                    }
                    else -> {
                        val str = msg.obj as String
                        a.handler(msg)

                    }
                }
            }
        }
    }

    fun showToastShort(s: String) {

        CustomToast.showToast(this, Gravity.CENTER, 0, s)

    }

    abstract fun handler(msg: Message)
}
