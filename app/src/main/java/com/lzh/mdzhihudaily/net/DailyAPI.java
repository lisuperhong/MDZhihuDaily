package com.lzh.mdzhihudaily.net;

import com.lzh.mdzhihudaily.base.StartImage;
import com.lzh.mdzhihudaily.module.newsDetail.model.NewsDetail;
import com.lzh.mdzhihudaily.module.newsList.model.News;
import com.lzh.mdzhihudaily.module.themeDaily.model.ThemeNews;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/13 0013  22:22
 * github: https://github.com/lisuperhong
 */
public interface DailyAPI {

    /**
     * 获取启动页图片
     * @author 李昭鸿
     * @date Created on 2016/7/13 0013 22:32
     */
    @GET("start-image/1080*1776")
    Observable<StartImage> startImage();

    /**
     * 获取最新消息
     * @author 李昭鸿
     * @date Created on 2016/7/13 0013 22:43
     */
    @GET("news/latest")
    Observable<News> newsLatest();

    /**
     * 获取过往消息
     * @author 李昭鸿
     * @date Created on 2016/7/13 0013 22:43
     */
    @GET("news/before/{date}")
    Observable<News> newBefore(@Path("date") String date);

    /**
     * 获取消息详情
     * @author 李昭鸿
     * @date Created on 2016/7/13 0013 22:44
     */
    @GET("news/{id}")
    Observable<NewsDetail> newsDetail(@Path("id") long id);

    /**
     * 根据主题id获得该主题日报列表
     * @author 李昭鸿
     * @date Created on 2016/7/14 0014 0:05
     */
    @GET("theme/{id}")
    Observable<ThemeNews> themeNews(@Path("id") int id);
}
