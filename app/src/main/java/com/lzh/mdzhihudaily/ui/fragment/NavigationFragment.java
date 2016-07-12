package com.lzh.mdzhihudaily.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.ui.adapter.NavigationMenuAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/9 0009 23:17
 * github: https://github.com/lisuperhong
 */
public class NavigationFragment extends Fragment {

    @Bind(R.id.imageView)
    ImageView userHead;
    @Bind(R.id.nav_menu_list)
    ListView navMenuList;

    private Context context;
    private NavigationMenuAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        adapter = new NavigationMenuAdapter(context);
        navMenuList.setAdapter(adapter);
        navMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (menuItemSelectedListener != null) {
                    menuItemSelectedListener.menuItemSelected(position, adapter.getItem(position));
                }
                adapter.setSelectedMenu(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.imageView)
    public void onClick() {

    }

    //选择回调的接口
    public interface OnMenuItemSelectedListener {
        void menuItemSelected(int position, String theme);
    }
    private OnMenuItemSelectedListener menuItemSelectedListener;

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener menuItemSelectedListener) {
        this.menuItemSelectedListener = menuItemSelectedListener;
    }
}
