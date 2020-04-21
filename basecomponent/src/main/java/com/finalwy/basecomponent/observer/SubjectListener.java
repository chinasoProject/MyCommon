package com.finalwy.basecomponent.observer;

/**
 * TODO 被观察者接口
 *
 * @author wy
 * @date 2020.4.20
 */
public interface SubjectListener {

    /**
     * 添加
     *
     * @param msgKey
     * @param observerListener
     */
    public void addObserver(int msgKey, ObserverListener observerListener);


    /**
     * 更新
     *
     * @param msgKey
     * @param content
     */
    public void sendObserver(int msgKey, Object content);


    /**
     * 删除
     *
     * @param msgKey
     */
    public void remove(int msgKey);
}
