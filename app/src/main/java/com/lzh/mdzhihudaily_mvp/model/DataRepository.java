package com.lzh.mdzhihudaily_mvp.model;

import com.lzh.mdzhihudaily_mvp.model.Entity.News;
import com.lzh.mdzhihudaily_mvp.model.Entity.NewsDetail;
import com.lzh.mdzhihudaily_mvp.model.Entity.StartImage;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeEntity;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeNews;
import com.lzh.mdzhihudaily_mvp.model.net.HttpMethod;
import com.lzh.mdzhihudaily_mvp.utils.DateUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * @author lzh
 * @desc:
 * @date Created on 2017/2/26 17:31
 * @github: https://github.com/lisuperhong
 */

public class DataRepository {

    private static class SingletonHolder {
        private static final DataRepository instance = new DataRepository();
    }

    private DataRepository() {

    }

    public static DataRepository getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 获取启动页图片
     * @author lzh
     * @date Create on 2017/2/27 下午11:06
     */
    public Observable<StartImage> getSplashImg() {
        return HttpMethod.getInstance().dailyAPI()
                .startImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取主题日报信息
     * @author lzh
     * @date Create on 2017/3/5 下午3:55
     */
    public Observable<List<ThemeEntity.Theme>> getThemes() {
        return HttpMethod.getInstance().dailyAPI()
                .themes()
                .map(new Func1<ThemeEntity, List<ThemeEntity.Theme>>() {
                    @Override
                    public List<ThemeEntity.Theme> call(ThemeEntity themeEntity) {
                        List<ThemeEntity.Theme> themes = themeEntity.getThemes();
                        return themes;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 获取最新的消息
     * @author lzh
     * @date Create on 2017/3/5 下午5:51
     */
    public Observable<News> getLatestNews(String date) {
        return HttpMethod.getInstance().dailyAPI()
                .newsLatest()
                .map(new MapNewStories(date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据日期获取过往的消息
     * @author lzh
     * @date Create on 2017/3/5 下午5:53
     */
    public Observable<News> getBeforeNews(String date) {
        return HttpMethod.getInstance().dailyAPI()
                .newBefore(date)
                .map(new MapNewStories(date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据主题id，获取该主题列表数据
     * @author lzh
     * @date Create on 2017/3/6 上午12:06
     */
    public Observable<ThemeNews> getThemeNews(int themeId) {
        return HttpMethod.getInstance().dailyAPI()
                .themeNews(themeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据storyId获取新闻详情
     * @author lzh
     * @date Create on 2017/3/6 下午11:45
     */
    public Observable<NewsDetail> getNewsDetail(long storyId) {
        return HttpMethod.getInstance().dailyAPI()
                .newsDetail(storyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private class MapNewStories implements Func1<News, News> {

        private String currentDate;

        public MapNewStories(String date) {
            currentDate = date;
        }

        @Override
        public News call(News news) {
            Logger.d(news);
            List<News.Story> newStories = new ArrayList<>();
            String dateString = news.getDate();
            if (dateString.equals(currentDate)) {
                for (News.Story story : news.getStories()) {
                    story.setStoryDate("今日热闻");
                    newStories.add(story);
                }
            } else {
                for (News.Story story : news.getStories()) {
                    story.setStoryDate(DateUtil.getStringDate(DateUtil.FORMAT_MM_DD, dateString) + " " + DateUtil.getDateWeek(dateString));
                    newStories.add(story);
                }
            }
            news.setStories(newStories);
            return news;
        }
    }
}
