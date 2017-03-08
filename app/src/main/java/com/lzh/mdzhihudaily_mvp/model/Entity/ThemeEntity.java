package com.lzh.mdzhihudaily_mvp.model.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Leo on 16/7/15.
 */
public class ThemeEntity {

    private int limit;
    private List<Long> subscribed;
    @SerializedName("others")
    private List<Theme> themes;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Long> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<Long> subscribed) {
        this.subscribed = subscribed;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> others) {
        this.themes = others;
    }


    public static class Theme {
        private int color;
        private String thumbnail;
        private String description;
        private int id;
        private String name;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
