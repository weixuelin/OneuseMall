package com.qyc.wxl.oneusemall.ui.user.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProBaseAdapter
import com.qyc.wxl.oneusemall.info.MessageInfo
import kotlinx.android.synthetic.main.item_message.view.*
import java.util.*

/**
 * Created by Administrator on 2017/10/12 0012.
 */

class MessageAdapter(context: Context, list: ArrayList<MessageInfo>) : ProBaseAdapter<MessageInfo>(context, list) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val vh = holder as VH
        val info = list[position]
//        vh.text_type.text = info.title
//        vh.text_content.text = info.content
    }


    override fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_message, parent, false)
        return VH(view)

    }

    override fun onUpdateHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {

    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val text_message_time = view.text_message_time


    }
}
