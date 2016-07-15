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
import com.lzh.mdzhihudaily.utils.DateUtil;
import com.lzh.mdzhihudaily.view.CustomTextSliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
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
    private String currentDate;
    private String beforeNewsDate;
    private List<Integer> datePositions;

    public NewsListAdapter(Context context, List<News.Story> stories, List<News.TopStory> topStories) {
        this.context = context;
        this.stories = stories;
        this.topStories = topStories;
        currentDate = DateUtil.dateToString("yyyyMMdd", DateUtil.getCurrentDate());
        beforeNewsDate = currentDate;
        datePositions = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView;
        if (viewType == TYPE_HEADER) {
            contentView = LayoutInflater.from(context).inflate(R.layout.news_list_item_header, parent, false);
            return new HeaderViewHolder(contentView);
        } else if (viewType == TYPE_WITH_DATE) {
            contentView = LayoutInflater.from(context).inflate(R.layout.news_list_item_date, parent, false);
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
            }
        } else if (holder instanceof DateViewHolder) {
            currentDate = beforeNewsDate;
            DateViewHolder viewHolder = (DateViewHolder) holder;
            bindItemView(viewHolder, position);
            News.Story story = stories.get(position - (topStories == null || topStories.isEmpty() ? 0 : 1));
            if (position == 1) {
                viewHolder.newsDate.setText("今日热闻");
                story.setTopDate("今日热闻");
            } else {
                Date date = DateUtil.stringToDate("yyyyMMdd", beforeNewsDate);
                String dateString = DateUtil.dateToString("M月d日", date) + " " + DateUtil.getDateWeek(date);
                viewHolder.newsDate.setText(dateString);
                story.setTopDate(dateString);
            }
            datePositions.add(position);
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            bindItemView(viewHolder, position);
        }
    }

    private void bindItemView(ItemViewHolder viewHolder, int position) {
        News.Story story = stories.get(position - (topStories == null || topStories.isEmpty() ? 0 : 1));
        viewHolder.newsTitle.setText(story.getTitle());
        Picasso.with(context)
                .load(story.getImages().get(0))
                .placeholder(R.mipmap.account_avatar)
                .into(viewHolder.newsImage);
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
        return currentDate.equals(beforeNewsDate) ? TYPE_ITEM : TYPE_WITH_DATE;
    }

    public void setRefreshDate(List<News.Story> stories, List<News.TopStory> topStories) {
        this.stories = stories;
        this.topStories = topStories;
        currentDate = DateUtil.dateToString("yyyyMMdd", DateUtil.getCurrentDate());
        beforeNewsDate = currentDate;
        notifyDataSetChanged();
    }

    public void setBeforeNews(String date, List<News.Story> beforeStories) {
        for (News.Story story : beforeStories) {
            stories.add(story);
        }
        this.beforeNewsDate = date;
        notifyDataSetChanged();
    }

    public List<News.Story> getStories() {
        return stories;
    }

    public List<Integer> getDatePositions() {
        return datePositions;
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
