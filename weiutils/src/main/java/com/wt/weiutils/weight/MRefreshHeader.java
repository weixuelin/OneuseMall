package com.wt.weiutils.weight;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.wt.weiutils.R;


/**
 * 类说明：自定义header
 * 作者：cx
 * 时间：2019/8/9
 */
public class MRefreshHeader extends LinearLayout implements RefreshHeader {

    private ImageView mProgressView;
    private AnimationDrawable mProgressDrawable;

    public MRefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public MRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public MRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        mProgressDrawable = new AnimationDrawable();
        //准备好资源图片
        int[] ids = {R.drawable.loading0, R.drawable.loading1, R.drawable.loading2, R.drawable.loading3, R.drawable.loading4, R.drawable.loading0, R.drawable.loading1, R.drawable.loading2, R.drawable.loading3, R.drawable.loading4,};
        //通过for循环添加每一帧动画
        for (int id : ids) {
            Drawable frame = getResources().getDrawable(id);
            //设定时长
            mProgressDrawable.addFrame(frame, 100);
        }
        mProgressView = new ImageView(context);
        mProgressView.setImageDrawable(mProgressDrawable);

        addView(mProgressView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        setMinimumHeight(DensityUtil.dp2px(55));
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int maxDragHeight) {
        mProgressDrawable.start();//开始动画
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        mProgressDrawable.stop();//停止动画
        return 500;//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
            case Refreshing:
                mProgressView.setVisibility(VISIBLE);//隐藏动画
                break;
            case ReleaseToRefresh:
                break;
        }
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int maxDragHeight) {
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
    }
}
