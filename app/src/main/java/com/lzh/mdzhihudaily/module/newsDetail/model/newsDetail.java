package com.lzh.mdzhihudaily.module.newsDetail.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/13 0013  22:39
 * github: https://github.com/lisuperhong
 */
public class NewsDetail implements Serializable {

    private static final long serialVersionUID = 7621473601709656883L;

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private List<String> js;
    private String ga_prefix;
    private int type;
    private long id;
    private List<String> css;
}
