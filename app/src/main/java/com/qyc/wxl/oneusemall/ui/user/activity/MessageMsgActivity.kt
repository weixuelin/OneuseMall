package com.qyc.wxl.oneusemall.ui.user.activity

import android.os.Message
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagua.forum.ui.listener.ItemClickListener
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import com.qyc.wxl.oneusemall.info.MessageInfo
import com.qyc.wxl.oneusemall.ui.user.adapter.MessageAdapter
import kotlinx.android.synthetic.main.activity_message_secont.*

/**
 * 消息详情
 */
class MessageMsgActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {

    }

    override fun setContentView(): Int {
        return R.layout.activity_message_secont
    }

    var status = ""
    override fun initData() {
        status = intent.getStringExtra("status")
        if (status == "1")
            titleBar!!.title = "系统消息"
        else
            titleBar!!.title = "订单消息"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        initAdapter()
    }

    override fun initListener() {
        refreshLayout.setOnLoadMoreListener {
            //            refreshLayout.finishLoadMore()//完成加载
//            refreshLayout.finishLoadMoreWithNoMoreData()//加载更多并标记无更多数据
        }
        refreshLayout.setOnRefreshListener {
            //            getData()
//            refreshLayout.finishRefresh()//刷新成功
//            refreshLayout.finishRefresh(false)//刷新失败
        }
    }

    //    private fun getData() {
//        val json = JSONObject()
//        json.put("token", Share.getToken(this))
//        HttpUtil.getInstance().postJson(Config.USER_INDEX_URL, json.toString(), Config.USER_INDEX_CODE, "", handler)
//        loadingDialog!!.show()
//    }
    var adapter: MessageAdapter? = null
    var collectList = arrayListOf<MessageInfo>()

    private fun initAdapter() {
        recycler_message.layoutManager = LinearLayoutManager(this)
        for (i in 0..3) {
            var info = MessageInfo()
            collectList.add(info)
        }
        adapter = MessageAdapter(this, collectList)
        recycler_message.adapter = adapter
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