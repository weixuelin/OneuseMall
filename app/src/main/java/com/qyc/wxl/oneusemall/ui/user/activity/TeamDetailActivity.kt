package com.qyc.wxl.oneusemall.ui.user.activity

import android.os.Message
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagua.forum.ui.listener.ItemClickListener
import com.qyc.wxl.oneusemall.R
import com.qyc.wxl.oneusemall.base.ProActivity
import com.qyc.wxl.oneusemall.info.MessageInfo
import com.qyc.wxl.oneusemall.ui.user.adapter.TeamDetailAdapter
import com.qyc.wxl.oneusemall.utils.DatePickDialogTwo
import com.wt.weiutils.time.bean.DateType
import kotlinx.android.synthetic.main.activity_team_detail.*
import java.text.SimpleDateFormat

/**
 * 我的团队明细
 */
class TeamDetailActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {

    }

    override fun setContentView(): Int {
        return R.layout.activity_team_detail
    }

    override fun initData() {
        titleBar!!.title = "我的明细"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        titleBar!!.setRightTitle("规则")
        titleBar!!.setRightColor(ContextCompat.getColor(this, R.color.white))
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
        image_team_time.setOnClickListener {
            val dialog = DatePickDialogTwo(this)
            //设置上下年分限制
            dialog.setYearLimt(200)
            //设置标题
            dialog.setTitle("选择日期")
            //设置类型
            dialog.setType(DateType.TYPE_YMD)
            //设置消息体的显示格式，日期格式
            dialog.setMessageFormat("yyyy-MM-dd")
            //设置选择回调
            dialog.setOnChangeLisener(null)
            //设置点击确定按钮回调
            dialog.show()
            dialog.setOnSureLisener { date, date1 ->
                val time_start = SimpleDateFormat("yyyy-MM-dd").format(date)
                val time_end = SimpleDateFormat("yyyy-MM-dd").format(date1)
                dialog.dismiss()
                text_team_time.text = time_start + "至" + time_end
            }

        }
    }

    //    private fun getData() {
//        val json = JSONObject()
//        json.put("token", Share.getToken(this))
//        HttpUtil.getInstance().postJson(Config.USER_INDEX_URL, json.toString(), Config.USER_INDEX_CODE, "", handler)
//        loadingDialog!!.show()
//    }
    var adapter: TeamDetailAdapter? = null
    var collectList = arrayListOf<MessageInfo>()

    private fun initAdapter() {
        recycler_team.layoutManager = LinearLayoutManager(this)
        for (i in 0..3) {
            var info = MessageInfo()
            collectList.add(info)
        }
        adapter = TeamDetailAdapter(this, collectList)
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