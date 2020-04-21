package com.finalwy.basecomponent.observer;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 观察者管理类
 *
 * @author wy
 * @date 2020.4.20
 */
public class ObserverManager implements SubjectListener {
    private static ObserverManager instance;
    private Map<String, ObserverListener> observerMap = new HashMap<>();

    public static ObserverManager getInstance() {
        if (null == instance) {
            synchronized (ObserverManager.class) {
                if (null == instance) {
                    instance = new ObserverManager();
                }
            }
        }
        return instance;
    }

    /**
     * 加入监听队列
     *
     * @param msgKey
     * @param observerListener
     */
    @Override
    public void addObserver(int msgKey, ObserverListener observerListener) {
        observerMap.put(msgKey + "", observerListener);
    }


    /**
     * 通知观察者刷新数据
     *
     * @param msgKey
     * @param content
     */
    @Override
    public void sendObserver(int msgKey, Object content) {
        for (Map.Entry<String, ObserverListener> entry : observerMap.entrySet()) {
            if (entry.getKey().equals(msgKey + "")) {
                entry.getValue().observerUpData(msgKey, content);
            }
        }
    }

    /**
     * 监听队列中移除
     *
     * @param msgKey
     */
    @Override
    public void remove(int msgKey) {
        String str = msgKey + "";
        if (observerMap.containsKey(str)) {
            observerMap.remove(str);
        }
    }

    /**
     * 清空监听队列
     */
    public void clearObserver() {
        if (observerMap == null || observerMap.size() == 0) {
            return;
        }
        observerMap.clear();
    }
}
