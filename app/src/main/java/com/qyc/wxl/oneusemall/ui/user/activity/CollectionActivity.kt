package com.qyc.wxl.oneusemall.ui.user.activity

import android.os.Message
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bagua.forum.ui.listener.ItemClickListener
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import com.qyc.wxl.oneusemall.info.MessageInfo
import com.qyc.wxl.oneusemall.ui.user.adapter.CollectionAdapter
import kotlinx.android.synthetic.main.activity_collection.*

/**
 * 我的收藏
 */
class CollectionActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {

    }

    override fun setContentView(): Int {
        return R.layout.activity_collection
    }

    override fun initData() {
        titleBar!!.title = "我的收藏"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        initAdapter()
    }

    override fun initListener() {

    }

    var adapter: CollectionAdapter? = null
    var collectList = arrayListOf<MessageInfo>()

    private fun initAdapter() {
        recycler_collection.layoutManager = GridLayoutManager(this, 2)
        for (i in 0..8) {
            var info = MessageInfo()
            collectList.add(info)
        }
        adapter = CollectionAdapter(this, collectList)
        recycler_collection.adapter = adapter
        adapter!!.getListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
//                val intent = Intent(this@FollowActivity, CenterActivity::class.java)
//                intent.putExtra("member_id", collectList[position].id.toString())
//                startActivity(intent)
            }

            override fun onLongClick(position: Int) {

            }
        })
    }
}