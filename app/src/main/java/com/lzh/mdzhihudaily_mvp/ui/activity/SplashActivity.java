package com.lzh.mdzhihudaily_mvp.ui.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzh.mdzhihudaily_mvp.R;
import com.lzh.mdzhihudaily_mvp.base.BaseActivity;
import com.lzh.mdzhihudaily_mvp.contract.SplashContract;
import com.lzh.mdzhihudaily_mvp.presenter.SplashPresenter;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @BindView(R.id.splash_img)
    ImageView imageView;
    @BindView(R.id.img_editor)
    TextView imgEditor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        presenter = new SplashPresenter(this);
    }

    @Override
    protected void initData() {
        presenter.start();
    }

    @Override
    public void defaultSplash() {
        imageView.setImageResource(R.drawable.splash);
    }

    @Override
    public void showSplash(String path) {
        Picasso.with(this)
                .load(path)
                .error(R.drawable.splash)
                .into(imageView);
    }

    @Override
    public void setImgEditor(String text) {
        imgEditor.setText(text);
    }

    @Override
    public void intentToMain() {
        final Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, 1000 * 2);
    }
}
