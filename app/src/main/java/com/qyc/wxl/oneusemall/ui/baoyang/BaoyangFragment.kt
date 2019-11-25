package com.qyc.wxl.oneusemall.ui.baoyang

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProV4Fragment


class BaoyangFragment : ProV4Fragment() {

    override fun handler(msg: Message) {

    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.activity_bang, container, false)
        isPrepared = true
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun loadData() {
        if (!isPrepared || !isVisable) {
            return
        } else {
            log("汽车保养可见")
        }
    }

}