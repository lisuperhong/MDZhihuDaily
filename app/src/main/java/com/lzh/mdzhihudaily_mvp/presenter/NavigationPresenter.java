package com.lzh.mdzhihudaily_mvp.presenter;

import android.support.annotation.NonNull;

import com.lzh.mdzhihudaily_mvp.base.BasePresenter;
import com.lzh.mdzhihudaily_mvp.model.DataRepository;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeEntity;
import com.lzh.mdzhihudaily_mvp.ui.fragment.NavigationFragment;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/5 16:01
 * @github: https://github.com/lisuperhong
 */

public class NavigationPresenter implements BasePresenter {

    private NavigationFragment navigationView;
    private Subscription subscription;

    public NavigationPresenter(@NonNull NavigationFragment fragment) {
        navigationView = fragment;
    }

    @Override
    public void start() {
        unsubscript();
        subscription = DataRepository.getInstance().getThemes()
                .subscribe(new Action1<List<ThemeEntity.Theme>>() {
                    @Override
                    public void call(List<ThemeEntity.Theme> themes) {
                        navigationView.setData(themes);
                    }
                });
    }

    @Override
    public void unsubscript() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
