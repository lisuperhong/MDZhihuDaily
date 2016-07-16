package com.lzh.mdzhihudaily.module.themeDaily.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzh.mdzhihudaily.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/16 0016  22:46
 * github: https://github.com/lisuperhong
 */
public class ThemeDailyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_EDITOR = 3;

    private Context context;
    private ThemeNews themeNews;

    public ThemeDailyListAdapter(Context context, ThemeNews themeNews) {
        this.context = context;
        this.themeNews = themeNews;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView;
        if (viewType == TYPE_HEADER) {
            contentView = LayoutInflater.from(context).inflate(R.layout.theme_list_item_header, parent, false);
            return new HeaderViewHolder(contentView);
        } else if (viewType == TYPE_EDITOR) {
            contentView = LayoutInflater.from(context).inflate(R.layout.theme_list_item_editor, parent, false);
            return new EditorViewHolder(contentView);
        } else if (viewType == TYPE_ITEM) {
            contentView = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false);
            return new ItemViewHolder(contentView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            viewHolder.themeDescription.setText(themeNews.getDescription());
            Picasso.with(context)
                    .load(themeNews.getBackground())
                    .into(viewHolder.themeImg);
        } else if (holder instanceof  EditorViewHolder) {
            EditorViewHolder viewHolder = (EditorViewHolder) holder;
            if (themeNews.getEditors() != null) {
                Picasso.with(context)
                        .load(themeNews.getEditors().get(0).getAvatar())
                        .into(viewHolder.editorImg);
            }
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            ThemeNews.Story story = themeNews.getStories().get(position - 2);
            viewHolder.newsTitle.setText(story.getTitle());
            if (story.getImages() != null) {
                viewHolder.newsImage.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(story.getImages().get(0))
                        .placeholder(R.mipmap.account_avatar)
                        .into(viewHolder.newsImage);
            } else {
                viewHolder.newsImage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return themeNews.getStories() == null ? 2 : themeNews.getStories().size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_EDITOR;
        } else {
            return TYPE_ITEM;
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.news_image)
        ImageView newsImage;
        @Bind(R.id.news_title)
        TextView newsTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.theme_img)
        ImageView themeImg;
        @Bind(R.id.theme_description)
        TextView themeDescription;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class EditorViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.editor_img)
        ImageView editorImg;

        public EditorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
