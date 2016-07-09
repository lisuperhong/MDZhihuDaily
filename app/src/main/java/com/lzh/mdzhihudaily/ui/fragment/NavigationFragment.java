package com.lzh.mdzhihudaily.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzh.mdzhihudaily.R;

/**
 *
 * @description
 * @author 李昭鸿
 * @date Created on 2016/7/9 0009 23:17
 * github: https://github.com/lisuperhong
 */
public class NavigationFragment extends Fragment {


    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

}
