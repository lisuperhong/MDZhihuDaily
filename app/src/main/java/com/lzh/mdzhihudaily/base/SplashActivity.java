package com.lzh.mdzhihudaily.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.net.HttpMethod;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/18 0018  23:22
 * github: https://github.com/lisuperhong
 */
public class SplashActivity extends AppCompatActivity {


    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.img_editor)
    TextView imgEditor;

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    private void initData() {
        context = this;
        HttpMethod.getInstance().dailyAPI()
                .startImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StartImage>() {
                    @Override
                    public void onCompleted() {
                        intentToMain();
                    }

                    @Override
                    public void onError(Throwable e) {
                        imageView.setImageResource(R.drawable.splash);
                        intentToMain();
                    }

                    @Override
                    public void onNext(StartImage startImage) {
                        if (startImage.getImg().isEmpty() || startImage.getImg().equals("null")) {
                            imageView.setImageResource(R.drawable.splash);
                        } else {
                            Picasso.with(context)
                                    .load(startImage.getImg())
                                    .error(R.drawable.splash)
                                    .into(imageView);
                            imgEditor.setText(startImage.getText());
                        }
                    }
                });

    }

    private void intentToMain() {
        final Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        timer.schedule(timerTask, 1000 * 3);
    }

}
