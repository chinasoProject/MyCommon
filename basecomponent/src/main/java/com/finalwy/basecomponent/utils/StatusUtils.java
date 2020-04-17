package com.finalwy.basecomponent.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.finalwy.basecomponent.R;
import com.finalwy.basecomponent.base.BaseApplication;
import com.finalwy.basecomponent.view.StatusLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 网络状态对应布局
 * @Author: wy
 * @CreateDate: 2020.4.17
 */
public class StatusUtils {

    private Activity mActivity;
    private ViewGroup mViewGroup;
    private static StatusLayout mStatusLayout;
    private FrameLayout mRlStatus;
    private static List<View> viewList = new ArrayList<>();
    private static List<Activity> activityList = new ArrayList<>();
    private static StatusUtils mStatusUtils;

    public StatusUtils(Activity activity) {
        this.mActivity = activity;
    }

    public StatusUtils(ViewGroup viewGroup) {
        mViewGroup = viewGroup;
    }

    public static StatusUtils create(Activity activity) {
        if (!activityList.contains(activity)) {
            mStatusUtils = new StatusUtils(activity);
            activityList.add(activity);
        }
        return mStatusUtils;
    }

    public static StatusUtils create(ViewGroup viewGroup) {
//        LogUtil.i("jxx","viewGroup："+viewGroup);

        if (!viewList.contains(viewGroup)) {
//            LogUtil.i("jxx","集合中没有，开始添加");
            mStatusUtils = new StatusUtils(viewGroup);
            viewList.add(viewGroup);
        } else {
//            LogUtil.i("jxx","集合有，不创建对象");
        }
        return mStatusUtils;
    }

    public void showLoading(int resID) {
        if (mActivity != null) {
            mRlStatus = mActivity.findViewById(R.id.rl_status);
            if (mRlStatus == null) {
                mStatusLayout = new StatusLayout(mActivity);
                mActivity.addContentView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mStatusLayout = (StatusLayout) mRlStatus.getParent();
            }
            mStatusLayout.setStatus(StatusLayout.STATUS_LOADING, resID);
        } else if (mViewGroup != null) {
            mRlStatus = mViewGroup.findViewById(R.id.rl_status);
            if (mRlStatus == null) {
                mStatusLayout = new StatusLayout(BaseApplication.getContext());
                mViewGroup.addView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mStatusLayout = (StatusLayout) mRlStatus.getParent();
            }
            mStatusLayout.setStatus(StatusLayout.STATUS_LOADING, resID);
        }
    }

    public void hint() {
        if (mActivity != null) {
            mRlStatus = mActivity.findViewById(R.id.rl_status);
            if (mRlStatus == null) {
                mStatusLayout = new StatusLayout(mActivity);
                mActivity.addContentView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mStatusLayout = (StatusLayout) mRlStatus.getParent();
            }
            mStatusLayout.setStatus(StatusLayout.STATUS_HIDE, 0);
        } else if (mViewGroup != null) {
            mRlStatus = mViewGroup.findViewById(R.id.rl_status);
            if (mRlStatus == null) {
                mStatusLayout = new StatusLayout(BaseApplication.getContext());
                mViewGroup.addView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mStatusLayout = (StatusLayout) mRlStatus.getParent();
            }
            mStatusLayout.setStatus(StatusLayout.STATUS_HIDE, 0);
        }
        //隐藏时销毁
        destroyInstance();
    }

    public void fail(View.OnClickListener onClickListener, int resID) {
        if (mActivity != null) {
            mRlStatus = mActivity.findViewById(R.id.rl_status);
            if (mRlStatus == null) {
                mStatusLayout = new StatusLayout(mActivity);
                mActivity.addContentView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mStatusLayout = (StatusLayout) mRlStatus.getParent();
            }
            mStatusLayout.setStatus(StatusLayout.STATUS_LOAD_FAIL, onClickListener, resID);
        } else if (mViewGroup != null) {
            mRlStatus = mViewGroup.findViewById(R.id.rl_status);
            if (mRlStatus == null) {
                mStatusLayout = new StatusLayout(BaseApplication.getContext());
                mViewGroup.addView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mStatusLayout = (StatusLayout) mRlStatus.getParent();
            }
            mStatusLayout.setStatus(StatusLayout.STATUS_LOAD_FAIL, onClickListener, resID);
        }
    }

    @SuppressLint("WrongConstant")
    public void noData(View.OnClickListener onClickListener, int resID) {
//        if (mActivity != null) {
//            mRlStatus = mActivity.findViewById(R.id.rl_status);
//            if (mRlStatus == null) {
//                mStatusLayout = new StatusLayout(mActivity);
//                mActivity.addContentView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            } else {
//                mStatusLayout = (StatusLayout) mRlStatus.getParent();
//            }
//            mStatusLayout.setStatus(StatusLayout.STATUS_LOAD_FAIL, onClickListener);
//        } else if (mViewGroup != null) {
//            mRlStatus = mViewGroup.findViewById(R.id.rl_status);
//            if (mRlStatus == null) {
//                mStatusLayout = new StatusLayout(WApplication.getContext());
//                mViewGroup.addView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            } else {
//                mStatusLayout = (StatusLayout) mRlStatus.getParent();
//            }
//            mStatusLayout.setStatus(StatusLayout.STATUS_NO_DATA, onClickListener);
//        },
        noData(onClickListener, "", resID);
    }

    @SuppressLint("WrongConstant")
    public void noData(View.OnClickListener onClickListener, String msg, int resID) {
        if (mActivity != null) {
            mRlStatus = mActivity.findViewById(R.id.rl_status);
            if (mRlStatus == null) {
                mStatusLayout = new StatusLayout(mActivity);
                mActivity.addContentView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mStatusLayout = (StatusLayout) mRlStatus.getParent();
            }
            if (TextUtils.isEmpty(msg)) {
                mStatusLayout.setStatus(StatusLayout.STATUS_NO_DATA, onClickListener, resID);
            } else {
                mStatusLayout.setStatus(StatusLayout.STATUS_NO_DATA, onClickListener, msg, resID);
            }
        } else if (mViewGroup != null) {
            mRlStatus = mViewGroup.findViewById(R.id.rl_status);
            if (mRlStatus == null) {
                mStatusLayout = new StatusLayout(BaseApplication.getContext());
                mViewGroup.addView(mStatusLayout, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mStatusLayout = (StatusLayout) mRlStatus.getParent();
            }
            if (TextUtils.isEmpty(msg)) {
                mStatusLayout.setStatus(StatusLayout.STATUS_NO_DATA, onClickListener, resID);
            } else {
                mStatusLayout.setStatus(StatusLayout.STATUS_NO_DATA, onClickListener, msg, resID);
            }
        }
    }

    public static void destroyInstance() {
        if (mStatusUtils != null) {
            mStatusUtils = null;
            mStatusLayout = null;
            viewList.clear();
            activityList.clear();
        }
    }

}
