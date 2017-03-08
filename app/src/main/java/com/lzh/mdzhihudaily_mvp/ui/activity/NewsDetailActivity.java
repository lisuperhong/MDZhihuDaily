package com.lzh.mdzhihudaily_mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzh.mdzhihudaily_mvp.R;
import com.lzh.mdzhihudaily_mvp.base.BaseActivity;
import com.lzh.mdzhihudaily_mvp.model.DataRepository;
import com.lzh.mdzhihudaily_mvp.model.Entity.NewsDetail;
import com.lzh.mdzhihudaily_mvp.utils.HtmlUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import rx.Subscriber;
import rx.Subscription;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/6 23:39
 * @github: https://github.com/lisuperhong
 */

public class NewsDetailActivity extends BaseActivity {

    private static final String STORY_ID = "story_id";

    @BindView(R.id.header_img)
    ImageView headerImg;
    @BindView(R.id.news_title)
    TextView newsTitle;
    @BindView(R.id.news_source)
    TextView newsSource;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_layout)
    CollapsingToolbarLayout collapsingLayout;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.nested_scroll)
    NestedScrollView nestedScroll;
    @BindView(R.id.cpb_loading)
    ContentLoadingProgressBar cpbLoading;

    private Context context;
    private Subscription subscription;

    public static void startActivity(Context context, long storyId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(STORY_ID, storyId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nestedScroll.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webview.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webview.getSettings().setLoadsImagesAutomatically(true);
        //设置 缓存模式
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webview.getSettings().setDomStorageEnabled(true);
        //为可折叠toolbar设置标题
        collapsingLayout.setTitleEnabled(false);
        collapsingLayout.setTitle(getString(R.string.app_name));
    }

    @Override
    protected void initData() {
        cpbLoading.setVisibility(View.VISIBLE);
        unsubscript();
        Intent intent = getIntent();
        long storyId = intent.getLongExtra(STORY_ID, 0);

        subscription = DataRepository.getInstance()
                .getNewsDetail(storyId)
                .subscribe(new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {
                        cpbLoading.setVisibility(View.GONE);
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

    private void unsubscript() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscript();
    }
}
