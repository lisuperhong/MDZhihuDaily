package com.lzh.mdzhihudaily.base;

import java.io.Serializable;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/13 0013  22:30
 * github: https://github.com/lisuperhong
 */
public class StartImage implements Serializable {

    private static final long serialVersionUID = 8861230522930047240L;

    private String text;
    private String img;

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }
}
