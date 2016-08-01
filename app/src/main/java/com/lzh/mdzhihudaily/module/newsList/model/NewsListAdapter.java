package com.lzh.mdzhihudaily.module.newsList.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.appinterface.RecyclerviewItemListener;
import com.lzh.mdzhihudaily.view.CustomTextSliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/13 0013  23:27
 * github: https://github.com/lisuperhong
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_WITH_DATE = 2;
    private static final int TYPE_ITEM = 3;

    private Context context;
    private List<News.Story> stories;
    private List<News.TopStory> topStories;
    private RecyclerviewItemListener itemClickListener;

    public NewsListAdapter(Context context, List<News.Story> stories, List<News.TopStory> topStories) {
        this.context = context;
        this.stories = stories;
        this.topStories = topStories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView;
        if (viewType == TYPE_HEADER) {
            contentView = LayoutInflater.from(context).inflate(R.layout.news_list_item_header, parent, false);
            return new HeaderViewHolder(contentView);
        } else if (viewType == TYPE_WITH_DATE) {
            contentView = LayoutInflater.from(context).inflate(R.layout.news_list_item_with_date, parent, false);
            return new DateViewHolder(contentView);
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
            viewHolder.sliderLayout.removeAllSliders();
            for (int i = 0; i < topStories.size(); i++) {
                final News.TopStory topStory = topStories.get(i);
                CustomTextSliderView textSliderView = new CustomTextSliderView(context);
                textSliderView
                        .description(topStory.getTitle())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .image(topStory.getImage());
                viewHolder.sliderLayout.addSlider(textSliderView);
                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        itemClickListener.onItemClick(topStory.getId());
                    }
                });
            }
        } else if (holder instanceof DateViewHolder) {
            DateViewHolder viewHolder = (DateViewHolder) holder;
            bindItemView(viewHolder, position, true);
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            bindItemView(viewHolder, position, false);
        }
    }

    private void bindItemView(ItemViewHolder viewHolder, int position, boolean isDateView) {
        final News.Story story = stories.get(position - (topStories == null || topStories.isEmpty() ? 0 : 1));
        viewHolder.newsTitle.setText(story.getTitle());
        if (isDateView) {
            viewHolder.newsDate.setText(story.getStoryDate());
        }
        Picasso.with(context)
                .load(story.getImages().get(0))
                .placeholder(R.mipmap.account_avatar)
                .into(viewHolder.newsImage);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(story.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stories.size() + (topStories == null || topStories.isEmpty() ? 0 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1) {
            return TYPE_WITH_DATE;
        }

        boolean isDateDiff = stories.get(position - 1).getStoryDate().equals(stories.get(position - 2).getStoryDate());
        return isDateDiff ? TYPE_ITEM : TYPE_WITH_DATE;
    }

    public void setRefreshDate(List<News.Story> stories, List<News.TopStory> topStories) {
        this.stories = stories;
        this.topStories = topStories;
        notifyDataSetChanged();
    }

    public void setBeforeNews(List<News.Story> beforeStories) {
        for (News.Story story : beforeStories) {
            stories.add(story);
        }
        notifyDataSetChanged();
    }

    public void setItemClickListener(RecyclerviewItemListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<News.Story> getStories() {
        return stories;
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

        @Bind(R.id.slider_layout)
        SliderLayout sliderLayout;
        @Bind(R.id.pager_indicator)
        PagerIndicator pagerIndicator;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            sliderLayout.setCustomIndicator(pagerIndicator);
        }
    }

    public static class DateViewHolder extends ItemViewHolder {

        @Bind(R.id.news_date)
        TextView newsDate;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
