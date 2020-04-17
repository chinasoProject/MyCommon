package com.finalwy.basecomponent.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;

import com.finalwy.basecomponent.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description:
 * @Author: wy
 * @CreateDate: 2020.4.17
 */
public class StatusLayout extends RelativeLayout {

    public static final int STATUS_LOADING = 1;
    public static final int STATUS_LOAD_FAIL = 2;
    public static final int STATUS_HIDE = 3;
    public static final int STATUS_NO_DATA = 4;
    private int mStatus = STATUS_LOADING;

    @IntDef({STATUS_LOADING, STATUS_LOAD_FAIL, STATUS_HIDE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Status {
    }

    private Context mContext;
    private ImageView mLlLoading;
    private TextView mTvFailMsg;

    public StatusLayout(Context context) {
        this(context, null);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.rl_status, this, true);
        mLlLoading = findViewById(R.id.ll_loading);
        mTvFailMsg = findViewById(R.id.tv_fail_msg);
//        GlideApp.with(mContext).load(R.mipmap.loading).into(mLlLoading);
        setOnClickListener(v -> {
            //拦截事件传递到StatusLayout下方
        });
    }

    /**
     * 设置多状态布局状态
     *
     * @param status @param status {@link Status}
     */
    public void setStatus(@Status int status, int resID) {
        this.mStatus = status;
        switchStatusLayout("", resID);
    }

    public void setStatus(@Status int status, OnClickListener onClickListener, int resID) {
        this.mStatus = status;
        switchStatusLayout("", resID);
        mTvFailMsg.setOnClickListener(onClickListener);
    }

    public void setStatus(@Status int status, OnClickListener onClickListener, String msg, int resID) {
        this.mStatus = status;
        switchStatusLayout(msg, resID);
        mTvFailMsg.setOnClickListener(onClickListener);
    }

    private void switchStatusLayout(String msg, int resID) {
        switch (mStatus) {
            case STATUS_LOADING:
                showLoading();
                break;
            case STATUS_LOAD_FAIL:
                showLoadFail();
                break;
            case STATUS_HIDE:
                hint();
                break;
            case STATUS_NO_DATA:
                showLoadNoData(msg, resID);
                break;
        }
    }

    private void showLoadFail() {
        // 判断网络是否可用
        boolean isAvailable = networkIsAvailable(getContext());
        if (isAvailable) {
            mTvFailMsg.setText(getContext().getString(R.string.load_error_try_again));
            mTvFailMsg.setCompoundDrawablesWithIntrinsicBounds(null
                    , ContextCompat.getDrawable(mContext, 0), null, null);
        } else {
            mTvFailMsg.setText(getContext().getString(R.string.net_error_try_again));
            mTvFailMsg.setCompoundDrawablesWithIntrinsicBounds(null
                    , ContextCompat.getDrawable(mContext, 0), null, null);
        }
        show();
        mLlLoading.setVisibility(GONE);
        mTvFailMsg.setVisibility(VISIBLE);
    }

    private void showLoadNoData(String msg, int resID) {
        if (TextUtils.isEmpty(msg)) {
            //为空显示默认提示语
            mTvFailMsg.setText(getContext().getString(R.string.no_data));
        } else {
            mTvFailMsg.setText(msg);

        }
        mTvFailMsg.setCompoundDrawablesWithIntrinsicBounds(null
                , ContextCompat.getDrawable(mContext, resID), null, null);
        show();
        mLlLoading.setVisibility(GONE);
        mTvFailMsg.setVisibility(VISIBLE);
    }

    private void showLoading() {
        show();
        mTvFailMsg.setVisibility(GONE);
        mLlLoading.setVisibility(VISIBLE);
    }

    private void show() {
        setVisibility(VISIBLE);
    }

    private void hint() {
        setVisibility(GONE);
    }

    /**
     * 检测网络是否可用
     */
    public boolean networkIsAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
