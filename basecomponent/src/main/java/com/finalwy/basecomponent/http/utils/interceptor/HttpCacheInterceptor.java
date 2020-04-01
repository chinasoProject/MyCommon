package com.finalwy.basecomponent.http.utils.interceptor;
import com.finalwy.basecomponent.utils.NetworkUtils;

import java.io.IOException;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * @author wy
 * @Date 2020-02-18
 */
public class HttpCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 有网络时 设置缓存超时时间1个小时
        int maxAge = 1 * 60;
        // 无网络时，设置超时为1天
        int maxStale = 60 * 60 * 24;
        Request request = chain.request();
        if (NetworkUtils.isConnected()) {
            //有网络时只从网络获取
            request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
        } else {
            //无网络时只从缓存中读取
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        Response response = chain.proceed(request);
        if (NetworkUtils.isConnected()) {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }
}
