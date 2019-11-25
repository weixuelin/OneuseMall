package com.qyc.wxl.oneusemall.base

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.DiffUtil
import com.bagua.forum.ui.listener.ItemClickListener
import com.wt.weiutils.utils.AdapterCallBack
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


abstract class ProBaseAdapter<T>(var context: Context, var list: ArrayList<T>) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    val HEAD: Int = 0
    val FOOT: Int = 2
    val NOMAL: Int = 3

    var inflater: LayoutInflater = LayoutInflater.from(context)
    val isHead: Boolean = false
    val isFooter: Boolean = false


    var itemClickListener: ItemClickListener? = null

    override fun getItemCount(): Int = list.size

    open fun getListener(item: ItemClickListener) {
        this.itemClickListener = item

    }
    fun longToTime(l: Long, type: String): String {
        val format = SimpleDateFormat(type, Locale.CHINA)

        return format.format(Date(l * 1000))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val viewHolder = onCreateView(parent, viewType)

        viewHolder.itemView.setOnClickListener {
            if (itemClickListener != null) {
                itemClickListener!!.onItemClick(viewHolder.adapterPosition)
            }
        }

        viewHolder.itemView.setOnLongClickListener {
            if (itemClickListener != null) {
                itemClickListener!!.onLongClick(viewHolder.adapterPosition)
            }
            true
        }

        return viewHolder

    }
    fun setAlpha(activity: Activity, f: Float) {
        val manager = activity.window.attributes
        manager.alpha = f
        activity.window.attributes = manager
    }
    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        Log.i("result", "payloads--------" + payloads.isEmpty())

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            onUpdateHolder(holder, position, payloads)
        }

    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> {
            if (isHead) {
                HEAD
            } else {
                NOMAL
            }
        }

        itemCount - 1 -> {
            if (isFooter) {
                FOOT
            } else {
                NOMAL
            }
        }

        else -> {
            NOMAL
        }
    }


    fun update(newList: ArrayList<T>, callBack: AdapterCallBack<T>) {
        val result = DiffUtil.calculateDiff(callBack, true)
        result.dispatchUpdatesTo(this)
        setData(newList)
    }

    fun setData(newList: ArrayList<T>) {
        list.clear()
        list.addAll(newList)
    }

    /**
     * 创建viewHolder
     */
    abstract fun onCreateView(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder

    /**
     * 更新数据信息
     */
    abstract fun onUpdateHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>)


    fun getW(context: Context): Int {
        val manager: WindowManager = (context as Activity).windowManager
        return manager.defaultDisplay.width
    }


    fun updateData(arr: ArrayList<T>) {
        list.addAll(arr)
        notifyDataSetChanged()
    }

    fun updateDataClear(arr: ArrayList<T>) {
        list.clear()
        list.addAll(arr)
        notifyDataSetChanged()
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
     * @param s
     * @return
     */
    fun floatToString(s: Float): String {
        val myFormat = DecimalFormat("#0.00")
        return myFormat.format(s.toDouble())
    }

    fun secondToMinute(time: Float): String {
        return if (time > 60) {
            val minuteNum = time / 60
            val mm = time % 60
            "${stringToInt(minuteNum.toString())}分${stringToInt(mm.toString())}秒"
        } else {
            "${stringToInt(time.toString())}秒"
        }

    }

    val ONE_DAY_MS = 24 * 60 * 60 * 1000


    /**
     * 获取两个时间戳间隔的天数
     */
    fun getDayByData(start: Long, end: Long): Int {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

        val fromCalendar: Calendar = Calendar.getInstance()
        fromCalendar.time = format.parse(format.format(start * 1000))
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0)
        fromCalendar.set(Calendar.SECOND, 0)
        fromCalendar.set(Calendar.MILLISECOND, 0)

        val toCalendar: Calendar = Calendar.getInstance()
        toCalendar.time = format.parse(format.format(end * 1000))

        toCalendar.set(Calendar.HOUR_OF_DAY, 0)
        toCalendar.set(Calendar.MINUTE, 0)
        toCalendar.set(Calendar.SECOND, 0)
        toCalendar.set(Calendar.MILLISECOND, 0)

        return ((toCalendar.time.time - fromCalendar.time.time) / (1000 * 60 * 60 * 24)).toInt()

    }


    /**
     * 集合后面添加数据
     */
    fun updateDataInfo(editInfo: T) {
        list.add(editInfo)

        notifyItemInserted(list.size - 1)

    }


}