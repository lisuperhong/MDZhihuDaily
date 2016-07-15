package com.lzh.mdzhihudaily.module.themeDaily;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzh.mdzhihudaily.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/11 0011 22:24
 * github: https://github.com/lisuperhong
 */
public class ThemeDailyFragment extends Fragment {

    public static final String KEY_THEME = "key_theme";
    public static final String THEME_ID = "theme_id";

    private String theme;

    @Bind(R.id.text)
    TextView text;

    public static ThemeDailyFragment newInstance(String theme, long id) {
        ThemeDailyFragment themeDailyFragment = new ThemeDailyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_THEME, theme);
        bundle.putLong(THEME_ID, id);
        themeDailyFragment.setArguments(bundle);
        return themeDailyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme_daily, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        theme = (String) getArguments().get(KEY_THEME);
        if (!TextUtils.isEmpty(theme)) {
            text.setText(theme);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
