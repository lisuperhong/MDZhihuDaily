package com.lzh.mdzhihudaily_mvp.contract;

import com.lzh.mdzhihudaily_mvp.base.BasePresenter;
import com.lzh.mdzhihudaily_mvp.base.BaseView;
import com.lzh.mdzhihudaily_mvp.model.Entity.News;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/5 16:53
 * @github: https://github.com/lisuperhong
 */

public interface NewsListContract {

    interface View extends BaseView {

        void stopRefreshLayout();

        void stopLoadMore();

        void setData(News news);

        void setBeforeData(News news);

        void onLoadMore();
    }

    interface Presenter extends BasePresenter {

        void refreshData();

        void loadMoreData();
    }
}
