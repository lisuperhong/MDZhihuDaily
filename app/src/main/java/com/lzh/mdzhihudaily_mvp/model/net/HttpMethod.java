package com.lzh.mdzhihudaily_mvp.model.net;

import com.lzh.mdzhihudaily_mvp.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/2/25 17:12
 * @github: https://github.com/lisuperhong
 */

public class HttpMethod {

    private Retrofit retrofit;
    private DailyAPI dailyAPI;

    private static class SingletonHolder {
        private static final HttpMethod instance = new HttpMethod();
    }

    public static HttpMethod getInstance() {
        return SingletonHolder.instance;
    }

    private HttpMethod() {
        retrofit = new Retrofit.Builder()
                .client(setOkHttpClient())
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    /**
     * 根据自身需求定义OkHttpClient
     * @author 李昭鸿
     * @date Created on 2016/7/13 0013 23:07
     */
    private OkHttpClient setOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    /**
     * 提供对外的网络访问接口
     * @author 李昭鸿
     * @date Created on 2016/7/13 0013 23:13
     */
    public DailyAPI dailyAPI() {
        if (dailyAPI == null) {
            dailyAPI = retrofit.create(DailyAPI.class);
        }
        return dailyAPI;
    }
}
