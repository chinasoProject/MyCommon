package com.finalwy.basecomponent.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.finalwy.basecomponent.observer.ObserverListener;
import com.finalwy.basecomponent.observer.ObserverManager;
import com.finalwy.basecomponent.utils.ContinuationClickUtils;
import com.finalwy.basecomponent.utils.ToastUtil;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author wy
 * @Date 2020-02-18
 */
public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseView, ObserverListener {
    protected String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected T mPresenter;
    private ToastUtil mToastUtil;
    private Unbinder unbinder;
    private CompositeDisposable mDisposables;
    protected List<String> msgKeyList = new ArrayList<>();

    protected abstract int getViewLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.base_AppTheme);
        setContentView(getViewLayout());
        unbinder = ButterKnife.bind(this);
        mContext = this;
        //注册eventbus
        Disposable disposable = RxBus.getDefault()
                .register(RxEvent.class, event -> {
                    int eventCode = event.getCode();
                    Log.e("RxBus", event.toString());
                    switch (eventCode) {
                        case RxEvent.EVENT_CLOSE_ALL_ACTIVITY:
                            break;
                        default:
                            onEvent(event);
                            break;
                    }
                });
        addDispose(disposable);
        mToastUtil = new ToastUtil(mContext);
        initData();
        initView();
    }


    protected void initView() {
    }

    protected void initData() {
    }

    protected void onEvent(RxEvent onEvent) {
    }


    /**
     * RxJava 添加订阅
     */
    protected void addDispose(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        //将所有disposable放入,集中处理
        mDisposables.add(disposable);
    }


    @Override
    public LifecycleTransformer bindLifecycle() {
        //RxAppCompatActivity中的方法bindToLifecycle()
        LifecycleTransformer objectLifecycleTransformer = bindToLifecycle();
        return objectLifecycleTransformer;
    }


    protected void showToast(String msg) {
        mToastUtil.showToast(msg);
    }

    protected void showLongToast(String msg) {
        mToastUtil.showToastLong(msg);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        config.fontScale = 1.0f;
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
        if (mDisposables != null) {
            RxBus.getDefault().unregister(mDisposables);
            mDisposables.clear();
            mDisposables = null;

        }
        //销毁页面时销毁已经存在的msgKey
        if (msgKeyList != null && msgKeyList.size() != 0) {
            for (int i = 0; i < msgKeyList.size(); i++) {
                removeMessage(Integer.parseInt(msgKeyList.get(i)));
            }
        }

    }

    /**
     * 是否点击过快
     *
     * @return
     */
    public boolean isFastClick() {
        return ContinuationClickUtils.isFastClick();
    }

    protected void observeMessage(int msgKey) {
        msgKeyList.add(msgKey + "");
        ObserverManager.getInstance().addObserver(msgKey, this);
    }

    protected void onReceiveMessage(int msgKey, Object msgObject) {

    }

    protected void sendMessage(int msgKey, Object msgObject) {
        ObserverManager.getInstance().sendObserver(msgKey, msgObject);
    }

    protected void removeMessage(int msgKey) {
        ObserverManager.getInstance().remove(msgKey);
    }

    @Override
    public void observerUpData(int msgKey, Object object) {
        onReceiveMessage(msgKey, object);
    }
}
