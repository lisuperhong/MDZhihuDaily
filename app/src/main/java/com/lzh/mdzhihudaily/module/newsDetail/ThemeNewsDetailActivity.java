package com.lzh.mdzhihudaily.module.newsDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.module.newsDetail.model.NewsDetail;
import com.lzh.mdzhihudaily.net.HttpMethod;
import com.lzh.mdzhihudaily.utils.HtmlUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/18 0018  21:49
 * github: https://github.com/lisuperhong
 */
public class ThemeNewsDetailActivity extends AppCompatActivity {

    private static final String STORY_ID = "story_id";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.cpb_loading)
    ContentLoadingProgressBar cpbLoading;

    private long storyId;

    public static void startActivity(Context context, long storyId) {
        Intent intent = new Intent(context, ThemeNewsDetailActivity.class);
        intent.putExtra(STORY_ID, storyId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_news_detail);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webview.getSettings().setLoadsImagesAutomatically(true);
        //设置 缓存模式
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webview.getSettings().setDomStorageEnabled(true);
        //为可折叠toolbar设置标题
    }

    private void initData() {
        Intent intent =getIntent();
        storyId = intent.getLongExtra(STORY_ID, 0);

        HttpMethod.getInstance().dailyAPI()
                .newsDetail(storyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        cpbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        cpbLoading.setVisibility(View.GONE);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        cpbLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        String htmlData = HtmlUtil.createHtmlData(newsDetail);
                        webview.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                    }
                });
    }
}
