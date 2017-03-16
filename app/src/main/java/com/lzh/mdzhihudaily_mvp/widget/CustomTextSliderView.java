package com.lzh.mdzhihudaily_mvp.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.lzh.mdzhihudaily_mvp.R;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/14 0014  21:43
 * github: https://github.com/lisuperhong
 */
public class CustomTextSliderView extends BaseSliderView {

    public CustomTextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.slider_item, null);
        ImageView target = (ImageView) v.findViewById(R.id.iv_slider);
        TextView title = (TextView) v.findViewById(R.id.tv_title);
        title.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}
