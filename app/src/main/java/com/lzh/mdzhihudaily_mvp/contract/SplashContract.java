package com.lzh.mdzhihudaily_mvp.contract;

import com.lzh.mdzhihudaily_mvp.base.BasePresenter;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/2/25 17:38
 * @github: https://github.com/lisuperhong
 */

public interface SplashContract {

    interface View {

        void defaultSplash();

        void showSplash(String path);

        void setImgEditor(String text);

        void intentToMain();
    }

    interface Presenter extends BasePresenter {
        void getSplash();
    }

}
