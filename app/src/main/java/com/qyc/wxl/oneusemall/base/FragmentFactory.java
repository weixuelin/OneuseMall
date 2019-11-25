package com.qyc.wxl.oneusemall.base;


import com.qyc.wxl.oneusemall.ui.baoyang.BaoyangFragment;
import com.qyc.wxl.oneusemall.ui.goods.GoodsFragment;
import com.qyc.wxl.oneusemall.ui.main.fragment.MainFragment;
import com.qyc.wxl.oneusemall.ui.order.OrderFragment;
import com.qyc.wxl.oneusemall.ui.user.fragment.UserFragment;

import androidx.fragment.app.Fragment;


public class FragmentFactory {

    /**
     * main
     *
     * @param position
     * @return
     */
    public static Fragment createForMain(int position, String type) {
        Fragment fragment = null;
        switch (position) {
            case 0:// 首页
                fragment = new MainFragment();
                break;
            case 1://商品
                fragment = new GoodsFragment();
                break;
            case 2://汽车保养
                fragment = new BaoyangFragment();
                break;
            case 3://  订单
                fragment = new OrderFragment();
                break;
            case 4://  个人中心
                fragment = new UserFragment();
                break;

        }
        return fragment;
    }

    public static Fragment createByLogin(int resId, String id, int postion, String model) {
        Fragment fragment = null;
        switch (resId) {
//            case R.id.relative_main_search://搜索
//                fragment = new SearchFragment();
//                break;

        }
        return fragment;
    }

}
