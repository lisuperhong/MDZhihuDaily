package com.lzh.mdzhihudaily_mvp.model.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/13 0013  21:57
 * github: https://github.com/lisuperhong
 */
public class News implements Serializable {

    private static final long serialVersionUID = -6739169218158952470L;

    private String date;
    private List<Story> stories;
    @SerializedName("top_stories")
    private List<TopStory> topStories;

    public static class Story implements Serializable {
        private static final long serialVersionUID = 5912170425503920137L;
        private List<String> images;
        private int type;
        private long id;
        private String ga_prefix;
        private String title;
        private String storyDate;

        public long getId() {
            return id;
        }

        public List<String> getImages() {
            return images;
        }

        public String getTitle() {
            return title;
        }

        public String getStoryDate() {
            return storyDate;
        }

        public void setStoryDate(String storyDate) {
            this.storyDate = storyDate;
        }

        @Override
        public String toString() {
            return "{" +
                    "ga_prefix='" + ga_prefix + '\'' +
                    ", images=" + images +
                    ", type=" + type +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public static class TopStory implements Serializable {
        private static final long serialVersionUID = 6351641634889292035L;
        private long id;
        private String image;
        private String title;
        private int type;
        private String ga_prefix;

        public long getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public String toString() {
            return "{" +
                    "ga_prefix='" + ga_prefix + '\'' +
                    ", id=" + id +
                    ", image='" + image + '\'' +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    public String getDate() {
        return date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public List<TopStory> getTopStories() {
        return topStories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    @Override
    public String toString() {
        return "News{" +
                "storyDate='" + date + '\'' +
                ", stories=" + stories +
                ", topStories=" + topStories +
                '}';
    }
}
