package com.lzh.mdzhihudaily_mvp.contract;

import com.lzh.mdzhihudaily_mvp.base.BasePresenter;
import com.lzh.mdzhihudaily_mvp.base.BaseView;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeNews;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/5 23:46
 * @github: https://github.com/lisuperhong
 */

public interface ThemeDailyContract {

    interface View extends BaseView {

        void stopRefreshLayout();

        void setData(ThemeNews themeNews);
    }

    interface Presenter extends BasePresenter {

        void refreshData();
    }
}
