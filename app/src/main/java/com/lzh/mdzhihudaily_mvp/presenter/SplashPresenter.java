package com.lzh.mdzhihudaily_mvp.presenter;

import android.support.annotation.NonNull;

import com.lzh.mdzhihudaily_mvp.contract.SplashContract;
import com.lzh.mdzhihudaily_mvp.model.DataRepository;
import com.lzh.mdzhihudaily_mvp.model.Entity.StartImage;

import rx.Subscriber;
import rx.Subscription;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/2/27 23:08
 * @github: https://github.com/lisuperhong
 */

public class SplashPresenter implements SplashContract.Presenter {

    private final SplashContract.View splashView;
    private Subscription subscription;

    public SplashPresenter(@NonNull SplashContract.View view) {
        splashView = view;
    }

    @Override
    public void start() {
        unsubscript();
        getSplash();
    }

    @Override
    public void getSplash() {
        subscription = DataRepository.getInstance()
                .getSplashImg()
                .subscribe(new Subscriber<StartImage>() {
                    @Override
                    public void onCompleted() {
                        splashView.intentToMain();
                    }

                    @Override
                    public void onError(Throwable e) {
                        splashView.defaultSplash();
                        splashView.intentToMain();
                    }

                    @Override
                    public void onNext(StartImage startImage) {
                        if (startImage.getImg().isEmpty() || startImage.getImg().equals("null")) {
                            splashView.defaultSplash();
                        } else {
                            splashView.setImgEditor(startImage.getText());
                            splashView.showSplash(startImage.getImg());
                        }
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
