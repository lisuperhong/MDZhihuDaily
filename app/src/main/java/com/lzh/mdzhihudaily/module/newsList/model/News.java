package com.lzh.mdzhihudaily.module.newsList.model;

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
    private List<TopStory> topStories;

    private static class Story {
        public List<String> images;
        private int type;
        public long id;
        public String ga_prefix;
        public String title;

        public long getId() {
            return id;
        }

        public List<String> getImages() {
            return images;
        }

        public String getTitle() {
            return title;
        }
    }

    private static class TopStory {
        public long id;
        public String images;
        public String title;
        private int type;
        public String ga_prefix;

        public long getId() {
            return id;
        }

        public String getImages() {
            return images;
        }

        public String getTitle() {
            return title;
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

}
