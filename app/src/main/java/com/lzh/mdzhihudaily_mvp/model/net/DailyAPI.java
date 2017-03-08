package com.lzh.mdzhihudaily_mvp.model.net;

import com.lzh.mdzhihudaily_mvp.model.Entity.News;
import com.lzh.mdzhihudaily_mvp.model.Entity.NewsDetail;
import com.lzh.mdzhihudaily_mvp.model.Entity.StartImage;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeEntity;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeNews;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/2/25 17:11
 * @github: https://github.com/lisuperhong
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
    @GET("news/before/{storyDate}")
    Observable<News> newBefore(@Path("storyDate") String date);

    /**
     * 获取消息详情
     * @author 李昭鸿
     * @date Created on 2016/7/13 0013 22:44
     */
    @GET("news/{id}")
    Observable<NewsDetail> newsDetail(@Path("id") long id);

    /**
     * 获取日报主题信息
     * @author 李昭鸿
     * @date Created on 2016/7/15 0015 21:40
     */
    @GET("themes")
    Observable<ThemeEntity> themes();

    /**
     * 根据主题id获得该主题日报列表
     * @author 李昭鸿
     * @date Created on 2016/7/14 0014 0:05
     */
    @GET("theme/{id}")
    Observable<ThemeNews> themeNews(@Path("id") int id);
}
