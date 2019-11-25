package com.qyc.wxl.oneusemall;

import android.app.Application;
import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.wt.weiutils.utils.ValuesUtils;
import com.wt.weiutils.weight.MRefreshHeader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiang on 2016-9-12.
 */
public class Apps extends Application {
    private final int QUEUE_NUMBER = 20;
    private static Context appContext;
    private static Apps apps;
    public static String getchannel;//渠道号
    public static List<String> inputList = new ArrayList<>();

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(com.wt.weiutils.R.color.app_bg, R.color.grey6);//全局设置主题颜色
                return new MRefreshHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        apps = this;
        getchannel = ValuesUtils.getAppMetaData(this, "UMENG_CHANNEL");
    }


    /**
     * 获得上下文对象
     *
     * @return
     */
    public static Context getAppContext() {
        return appContext;
    }

    /**
     * 获得应用对象
     */
    public static Apps getApps() {
        return apps;
    }


}
