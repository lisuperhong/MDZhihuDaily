package com.lzh.mdzhihudaily_mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> 21f335550fbbbc038b9f5be71b4090f37bac2b67

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lzh
 * @desc: Activity基类
 * @date Created on 2017/2/25 15:11
 * @github: https://github.com/lisuperhong
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private Unbinder unbinder;
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
<<<<<<< HEAD
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
=======
>>>>>>> 21f335550fbbbc038b9f5be71b4090f37bac2b67
        unbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
<<<<<<< HEAD
        ActivityCollector.removeActivity(this);
=======
>>>>>>> 21f335550fbbbc038b9f5be71b4090f37bac2b67
        unbinder.unbind();
        if (presenter != null) {
            presenter.unsubscript();
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();
}
