package com.wt.weiutils.weight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wt.weiutils.R;
import com.wt.weiutils.utils.StringUtils;
import com.wt.weiutils.utils.ValuesUtils;

import java.util.Objects;


public class LoadingDialog extends Dialog {

    private static final String TAG = "LoadingDialog";

    private boolean mCancelable;
    private View v;
    private TextView tv;
    private View spin_kit_b, rl;

    public LoadingDialog(Context context, String msg) {
        this(context, R.style.LoadingDialog, false, msg);
    }

    public LoadingDialog(Activity activity, String msg) {
        this(activity, R.style.LoadingDialog, false, msg);
    }

    public LoadingDialog(Context context, int themeResId, boolean cancelable, String msg) {
        super(context, themeResId);
        v = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        spin_kit_b = v.findViewById(R.id.spin_kit_b);
        rl = v.findViewById(R.id.rl);
        tv = v.findViewById(R.id.tv);
        if (StringUtils.isEmptyWithTrim(msg)) {
            spin_kit_b.setVisibility(View.VISIBLE);
            rl.setVisibility(View.GONE);
        } else {
            spin_kit_b.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
            tv.setText(msg);
        }
        mCancelable = cancelable;
    }

    @Override
    public void show() {
        if (!this.isShowing()) {
            super.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        initView();
    }

    @SuppressLint("NewApi")
    private void initView() {
        setContentView(v);
        // 设置窗口大小
        int wh = ValuesUtils.getDimen(getContext(), R.dimen.qb_px_200);
        WindowManager.LayoutParams attributes = Objects.requireNonNull(getWindow()).getAttributes();
        attributes.alpha = 0.8f;
        attributes.width = wh;
        attributes.height = wh;
        getWindow().setAttributes(attributes);
        setCancelable(mCancelable);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 屏蔽返回键
            return mCancelable;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setMsg(String msg) {
        if (StringUtils.isEmptyWithTrim(msg)) {
            spin_kit_b.setVisibility(View.VISIBLE);
            rl.setVisibility(View.GONE);
        } else {
            spin_kit_b.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
            tv.setText(msg);
        }
    }
}
