package com.lzh.mdzhihudaily.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.constant.APIConstant;
import com.lzh.mdzhihudaily.module.themeDaily.model.ThemeEntity;
import com.lzh.mdzhihudaily.net.DailyAPI;
import com.lzh.mdzhihudaily.net.HttpMethod;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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
                .map(new Func1<ThemeEntity, List<ThemeEntity.Theme>>() {
                    @Override
                    public List<ThemeEntity.Theme> call(ThemeEntity themeEntity) {
                        List<ThemeEntity.Theme> themes = themeEntity.getThemes();
                        return themes;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ThemeEntity.Theme>>() {
                    @Override
                    public void call(List<ThemeEntity.Theme> themes) {
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
                    if (position == 0) {
                        menuItemSelectedListener.menuItemSelected(position, adapter.getThemes().get(0));
                    } else {
                        menuItemSelectedListener.menuItemSelected(position, adapter.getThemes().get(position - 1));
                    }
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
        void menuItemSelected(int position, ThemeEntity.Theme theme);
    }
    private OnMenuItemSelectedListener menuItemSelectedListener;

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener menuItemSelectedListener) {
        this.menuItemSelectedListener = menuItemSelectedListener;
    }
}
