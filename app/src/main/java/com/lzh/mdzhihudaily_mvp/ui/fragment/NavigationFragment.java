package com.lzh.mdzhihudaily_mvp.ui.fragment;

import android.widget.ImageView;
import android.widget.ListView;

import com.lzh.mdzhihudaily_mvp.R;
import com.lzh.mdzhihudaily_mvp.base.BaseFragment;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeEntity;
import com.lzh.mdzhihudaily_mvp.presenter.NavigationPresenter;
import com.lzh.mdzhihudaily_mvp.ui.adapter.NavigationMenuAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/5 14:53
 * @github: https://github.com/lisuperhong
 */

public class NavigationFragment extends BaseFragment {

    @BindView(R.id.imageView)
    ImageView userHead;
    @BindView(R.id.nav_menu_list)
    ListView navMenuList;

    private NavigationMenuAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView() {
        presenter = new NavigationPresenter(this);
    }

    @Override
    protected void initData() {
        presenter.start();
    }

    public void setData(List<ThemeEntity.Theme> themes) {
        adapter = new NavigationMenuAdapter(getActivity(), themes);
        navMenuList.setAdapter(adapter);
    }

    @OnItemClick(R.id.nav_menu_list)
    void onItemSelected(int position) {
        if (menuItemSelectedListener != null) {
            if (position == 0) {
                menuItemSelectedListener.menuItemSelected(position, adapter.getThemes().get(0));
            } else {
                menuItemSelectedListener.menuItemSelected(position, adapter.getThemes().get(position - 1));
            }
        }
        adapter.setSelectedMenu(position);
    }

    // 定义选中主题后回调的接口
    public interface OnMenuItemSelectedListener {
        void menuItemSelected(int position, ThemeEntity.Theme theme);
    }

    private OnMenuItemSelectedListener menuItemSelectedListener;

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener menuItemSelectedListener) {
        this.menuItemSelectedListener = menuItemSelectedListener;
    }
}
