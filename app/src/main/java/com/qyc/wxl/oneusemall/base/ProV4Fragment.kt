package com.qyc.wxl.oneusemall.base

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.os.*
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.qyc.wxl.oneusemall.R
import com.wt.weiutils.utils.AppManager
import com.wt.weiutils.utils.CustomToast
import com.wt.weiutils.weight.LoadingDialog
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.DecimalFormat

abstract class ProV4Fragment : androidx.fragment.app.Fragment() {

    open var gson: Gson? = null
    var activity: Activity? = null

    var uid: Int = 0
    var token: String = ""
    var handler: Handler? = null

    var inPutManager: InputMethodManager? = null
    protected var isVisable: Boolean = false
    var isPrepared = false

    /**
     * 创建view
     */
    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return createView(inflater, container, savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisable = true
            onVisible()
        } else {
            isVisable = false
            onInVisible()
        }
    }

    private fun onInVisible() {}

    private fun onVisible() {
        //  加载数据
        loadData()
    }

    protected fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setColor(activity!!, ContextCompat.getColor(activity!!, R.color.titlebar_color), 0)
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else
            StatusBarUtil.setColor(activity!!, ContextCompat.getColor(activity!!, R.color.black), 68)
    }


    fun setStatusBarColorChild(view: View) {
        StatusBarUtil.setTranslucentForImageView(activity!!, 0, view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            val p = view.parent as View
            p?.setBackgroundColor(Color.parseColor("#33000000"))
        }
    }

    protected fun setTranslucentForImageView(isTranslucent: Boolean, view: View) {
        if (isTranslucent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarUtil.setTranslucentForImageView(activity!!, 0, view)
                activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else
                StatusBarUtil.setTranslucentForImageView(activity!!, 84, view)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarUtil.setColor(activity!!, ContextCompat.getColor(activity!!, R.color.titlebar_color), 0)
                activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else
                StatusBarUtil.setColor(activity!!, ContextCompat.getColor(activity!!, R.color.colorMain), 84)
        }
    }
    protected abstract fun loadData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity()
        uid = Share.getUid(activity!!)
        token = Share.getToken(activity!!)
        customProgressDialog = LoadingDialog(context, "")

        handler = ProHandler(this)
        gson = Gson()
        inPutManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        setStatusBar()
    }

    /**
     * 隐藏输入法
     */
    fun hideInPut(view: View) {
        if (inPutManager != null) {
            inPutManager!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    abstract fun handler(msg: Message)


    companion object {

        /**
         * handler数据
         */
        class ProHandler(proV4Fragment: ProV4Fragment) : Handler(Looper.getMainLooper()) {
            var weak: WeakReference<ProV4Fragment>? = null

            init {
                weak = WeakReference(proV4Fragment)

            }

            val a = weak!!.get()

            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)

                val str = msg!!.obj as String

                if (str != "" && str != "null") {
                    if (str.contains("code")) {
                        val json = JSONObject(str)
                        val code = json.optInt("code")

                        if (code == 501) {

                            a!!.showToastShort(a.activity!!, json.optString("msg"))

                            Share.clearUidOrToken(a.activity!!)

                            /// 重启app
                            val i: Intent = a.activity!!.baseContext.packageManager.getLaunchIntentForPackage(a.activity!!.baseContext.packageName)!!
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            a.activity!!.startActivity(i)
                            AppManager.getInstance().finishActivity(a.activity)
                        } else {
                            a!!.handler(msg)
                        }
                    } else {
                        a!!.handler(msg)
                    }
                } else {
                    a!!.handler(msg)
                }

            }
        }


        /**
         * 异步任务处理视频第一帧图片
         */
        class A(val callBack: OnGetBitmapCallBack?) : AsyncTask<String, Int, Bitmap>() {

            override fun doInBackground(vararg p0: String?): Bitmap? {
                val mmr = MediaMetadataRetriever()
                val videoUrl = p0[0]

                return try {

                    mmr.setDataSource(videoUrl, HashMap<String, String>())

                    val bitmap = mmr.frameAtTime  /// 获取第一帧图片

                    mmr.release()  // 释放资源

                    bitmap

                } catch (e: IllegalArgumentException) {
                    null
                }

            }

            override fun onPostExecute(result: Bitmap?) {
                super.onPostExecute(result)
                callBack?.onBitmap(result!!)

            }
        }


    }


    interface OnGetBitmapCallBack {
        fun onBitmap(bitmap: Bitmap)
    }


    val isDebug: Boolean = true

    fun log(message: String) {
        if (isDebug) {
            Log.i("toby", message)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
    }


    fun showShortToast(activity: Activity, str: String, time: Int = Toast.LENGTH_SHORT) {
        CustomToast.showToast(activity, Gravity.CENTER, 0, str)
    }


    fun setAlpha(activity: Activity, f: Float) {
        val manager = activity.window.attributes
        manager.alpha = f
        activity.window.attributes = manager
    }


    fun showToastShort(context: Context, str: String) {
        CustomToast.showToast(context, Gravity.CENTER, 0, str)
    }

    var customProgressDialog: LoadingDialog? = null
    fun secondToMinute(time: Float): String {
        return if (time > 60) {
            val minuteNum = time / 60
            val mm = time % 60

            "${stringToInt(minuteNum.toString())}分${stringToInt(mm.toString())}秒"

        } else {
            "${stringToInt(time.toString())}秒"
        }

    }


    /**
     * 保留整数
     */
    fun stringToInt(str: String): String {
        val format: DecimalFormat = DecimalFormat("#0")
        return format.format(BigDecimal(str))
    }

    /**
     * 保留两位小数
     */
    fun floatToString(ff: Float): String {
        val format: DecimalFormat = DecimalFormat("#0.00")
        return format.format(ff)
    }

    /**
     * 复制信息到剪切板
     */
    fun copy(context: Context, text: String) {
        val manager: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manager.text = text

    }

    /**
     * 移除load加载框
     */
    fun removeLoadDialog() {
        if (customProgressDialog != null) {
            if (customProgressDialog!!.isShowing) {
                customProgressDialog!!.dismiss()
            }
        }
    }


    fun setLine(searchView: SearchView, resId: Int) {
        try {
            val argClass = searchView.javaClass
            val ownField = argClass.getDeclaredField("mSearchPlate")
            ownField.isAccessible = true
            val mView = ownField.get(searchView) as View
            mView.setBackgroundColor(resId)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    var viewList: ArrayList<View>? = arrayListOf()

    fun getW(activity: Activity): Int {
        val att = activity.windowManager.defaultDisplay
        return att.width
    }

    fun getH(activity: Activity): Int {
        val att = activity.windowManager.defaultDisplay;
        return att.height
    }


}