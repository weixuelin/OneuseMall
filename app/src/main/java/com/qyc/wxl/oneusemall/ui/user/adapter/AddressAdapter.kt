package com.qyc.wxl.oneusemall.ui.user.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.BaseAdapter
import com.qyc.wxl.oneusemall.info.MessageInfo
import kotlinx.android.synthetic.main.item_address.view.*
import java.util.*

/**
 * Created by Administrator on 2017/10/12 0012.
 */

class AddressAdapter(context: Context, list: ArrayList<MessageInfo>,click:View.OnClickListener) : BaseAdapter<MessageInfo>(context, list,click) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val vh = holder as VH
        val info = list[position]
//        vh.text_type.text = info.title
//        vh.text_content.text = info.content
        if (position == 0) {
            vh.image_address_mo.setVisibility(View.VISIBLE)
        } else {
            vh.image_address_mo.setVisibility(View.GONE)
        }
    }


    override fun onCreateView(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.item_address, parent, false)
        return VH(view)

    }

    override fun onUpdateHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {

    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val linear_address_delete = view.linear_address_delete
        val image_address_mo = view.image_address_mo

    }
}
