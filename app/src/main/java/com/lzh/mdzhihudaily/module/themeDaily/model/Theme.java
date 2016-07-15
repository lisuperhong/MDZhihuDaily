package com.lzh.mdzhihudaily.module.themeDaily.model;

/**
 * Created by Leo on 16/7/15.
 */
public class Theme {

    private String description;
    private long id;
    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
