package com.lzh.mdzhihudaily.base;

import android.support.v4.app.Fragment;

import rx.Subscription;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/14 0014  22:27
 * github: https://github.com/lisuperhong
 */
public class BaseFragment extends Fragment {

    protected Subscription subscription;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
