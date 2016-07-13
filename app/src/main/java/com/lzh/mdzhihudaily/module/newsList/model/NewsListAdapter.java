package com.lzh.mdzhihudaily.module.newsList.model;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/13 0013  23:27
 * github: https://github.com/lisuperhong
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_TOP_STORY = 1;
    private static final int TYPE_DATE = 3;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
