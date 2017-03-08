package com.lzh.mdzhihudaily_mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzh.mdzhihudaily_mvp.R;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/5 15:23
 * @github: https://github.com/lisuperhong
 */

public class NavigationMenuAdapter extends BaseAdapter {

    private Context context;
    private List<ThemeEntity.Theme> themes;
    private int selectedMenu;

    public NavigationMenuAdapter(Context context, List<ThemeEntity.Theme> themes) {
        this.context = context;
        this.themes = themes;
    }

    @Override
    public int getCount() {
        return themes == null ? 1 : themes.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return themes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.navigation_menu_item, null);
            viewHolder = new MenuViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MenuViewHolder) convertView.getTag();
        }

        if (position == 0) {
            viewHolder.leftImg.setVisibility(View.VISIBLE);
            viewHolder.menuText.setTextColor(context.getResources().getColor(R.color.menu_main));
        } else {
            viewHolder.leftImg.setVisibility(View.GONE);
            viewHolder.menuText.setTextColor(context.getResources().getColor(R.color.black));
        }

        if (position == selectedMenu) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.menu_selected));
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        if (position == 0) {
            viewHolder.menuText.setText("首页");
        } else {
            viewHolder.menuText.setText(themes.get(position - 1).getName());
        }
        return convertView;
    }

    public void setSelectedMenu(int selectedMenu) {
        this.selectedMenu = selectedMenu;
        notifyDataSetChanged();
    }

    public List<ThemeEntity.Theme> getThemes() {
        return themes;
    }

    static class MenuViewHolder {
        @BindView(R.id.left_img) ImageView leftImg;
        @BindView(R.id.menu_text) TextView menuText;
        @BindView(R.id.menu_arrow) ImageView arrow;

        public MenuViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
