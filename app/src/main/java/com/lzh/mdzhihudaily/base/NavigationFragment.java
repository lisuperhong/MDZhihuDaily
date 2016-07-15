package com.lzh.mdzhihudaily.base;


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
import com.lzh.mdzhihudaily.module.themeDaily.model.Theme;
import com.lzh.mdzhihudaily.net.HttpMethod;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/9 0009 23:17
 * github: https://github.com/lisuperhong
 */
public class NavigationFragment extends BaseFragment {

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
        initListener();
        initData();
    }

    private void initData() {
        unsubscribe();
        subscription = HttpMethod.getInstance().dailyAPI()
                .themes()
                .map(new Func1<String, List<Theme>>() {
                    @Override
                    public List<Theme> call(String s) {
                        List<Theme> themes = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONObject(s).getJSONArray("others");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Theme theme = new Theme();
                                JSONObject json = jsonArray.getJSONObject(i);
                                theme.setDescription(json.getString("description"));
                                theme.setId(json.getLong("id"));
                                theme.setName(json.getString("name"));
                                themes.add(theme);
                            }
                        } catch (JSONException e) {
                            Logger.e(e, "Json异常");
                        }
                        return themes;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Theme>>() {
                    @Override
                    public void call(List<Theme> themes) {
                        adapter = new NavigationMenuAdapter(context, themes);
                        navMenuList.setAdapter(adapter);
                    }
                });
    }

    private void initListener() {
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

    //选择回调的接口
    public interface OnMenuItemSelectedListener {
        void menuItemSelected(int position, Theme theme);
    }
    private OnMenuItemSelectedListener menuItemSelectedListener;

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener menuItemSelectedListener) {
        this.menuItemSelectedListener = menuItemSelectedListener;
    }
}
