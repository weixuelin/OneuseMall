package com.qyc.wxl.oneusemall.ui.user.activity

import android.content.Intent
import android.os.Message
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagua.forum.ui.listener.ItemClickListener
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import com.qyc.wxl.oneusemall.info.MessageInfo
import com.qyc.wxl.oneusemall.ui.user.adapter.TeamAdapter
import kotlinx.android.synthetic.main.activity_team.*

/**
 * 我的团队
 */
class TeamActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {

    }

    override fun setContentView(): Int {
        return R.layout.activity_team
    }

    override fun initData() {
        titleBar!!.title = "我的团队"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        titleBar!!.setRightTitle("我的明细")
        titleBar!!.setRightColor(ContextCompat.getColor(this, R.color.white))
        initAdapter()
    }

    override fun onRightClick(v: View) {
        startActivity(Intent(this, TeamDetailActivity::class.java))

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
    var adapter: TeamAdapter? = null
    var collectList = arrayListOf<MessageInfo>()

    private fun initAdapter() {
        recycler_team.layoutManager = LinearLayoutManager(this)
        for (i in 0..3) {
            var info = MessageInfo()
            collectList.add(info)
        }
        adapter = TeamAdapter(this, collectList)
        recycler_team.adapter = adapter
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