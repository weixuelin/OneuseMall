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
import com.qyc.wxl.oneusemall.ui.user.adapter.AddressAdapter
import kotlinx.android.synthetic.main.activity_address.*

/**
 * 收货地址
 */
class AddressActivity : ProActivity() {

    override fun handler(msg: Message) {

    }

    override fun initView() {
    }

    override fun setContentView(): Int {
        return R.layout.activity_address
    }

    override fun initData() {
        titleBar!!.title = "收货地址"
        titleBar!!.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        titleBar!!.setRightTitle("添加")
        titleBar!!.setRightColor(ContextCompat.getColor(this, R.color.white))
        initAdapter()
    }

    override fun onRightClick(v: View) {
        //添加新地址
        val intent = Intent(this, AddAddressActivity::class.java)
        intent.putExtra("status", "1")
        startActivity(intent)
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

    var adapter: AddressAdapter? = null
    var collectList = arrayListOf<MessageInfo>()

    private fun initAdapter() {
        recycler_address.layoutManager = LinearLayoutManager(this)
        for (i in 0..3) {
            var info = MessageInfo()
            collectList.add(info)
        }
        adapter = AddressAdapter(this, collectList, listener)
        recycler_address.adapter = adapter
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

    private val listener = View.OnClickListener {
        when (it.id) {
            R.id.linear_address_edit -> {
                //编辑
                val intent = Intent(this, AddAddressActivity::class.java)
                intent.putExtra("id", "1")
                startActivity(intent)
            }
            R.id.linear_address_delete -> {
                //删除
            }
        }
    }
}