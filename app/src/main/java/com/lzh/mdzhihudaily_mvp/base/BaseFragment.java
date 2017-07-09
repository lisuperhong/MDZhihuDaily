package com.lzh.mdzhihudaily_mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lzh
 * @desc: Fragment基类
 * @date Created on 2017/2/25 16:45
 * @github: https://github.com/lisuperhong
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private Unbinder unbinder;
    protected T presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BaseFragment", getClass().getSimpleName());
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (presenter != null) {
            presenter.unsubscript();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
