package com.lzh.mdzhihudaily.module.newsDetail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.module.newsDetail.model.NewsDetail;
import com.lzh.mdzhihudaily.net.HttpMethod;
import com.lzh.mdzhihudaily.utils.HtmlUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/13 0013 23:52
 * github: https://github.com/lisuperhong
 */
public class NewsDetailActivity extends AppCompatActivity {

    private static final String STORY_ID = "story_id";

    @Bind(R.id.header_img)
    ImageView headerImg;
    @Bind(R.id.news_title)
    TextView newsTitle;
    @Bind(R.id.news_source)
    TextView newsSource;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingLayout;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.nested_scroll)
    NestedScrollView nestedScroll;
    @Bind(R.id.cpb_loading)
    ContentLoadingProgressBar cpbLoading;

    private Context context;
    private long storyId;

    public static void startActivity(Context context, long storyId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(STORY_ID, storyId);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nestedScroll.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webview.getSettings().setLoadsImagesAutomatically(true);
        //设置 缓存模式
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webview.getSettings().setDomStorageEnabled(true);
        //为可折叠toolbar设置标题
//        collapsingLayout.setTitle(getString(R.string.toolbar_title));
    }

    private void initData() {
        context = this;
        Intent intent = getIntent();
        storyId = intent.getLongExtra(STORY_ID, 0);

        HttpMethod.getInstance().dailyAPI()
                .newsDetail(storyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        cpbLoading.setVisibility(View.GONE);
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        cpbLoading.setVisibility(View.VISIBLE);
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
                        cpbLoading.setVisibility(View.GONE);
                        Picasso.with(context)
                                .load(newsDetail.getImage())
                                .into(headerImg);
                        newsTitle.setText(newsDetail.getTitle());
                        newsSource.setText(newsDetail.getImage_source());
                        String htmlData = HtmlUtil.createHtmlData(newsDetail);
                        webview.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                    }
                });
    }
}