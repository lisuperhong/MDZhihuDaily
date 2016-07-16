package com.lzh.mdzhihudaily.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.module.themeDaily.model.ThemeEntity;

import java.util.List;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/9 0009  23:52
 * github: https://github.com/lisuperhong
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
        return themes == null ? 0 : themes.size();
    }

    @Override
    public ThemeEntity.Theme getItem(int position) {
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
            viewHolder = new MenuViewHolder();
            viewHolder.leftImg = (ImageView) convertView.findViewById(R.id.left_img);
            viewHolder.menuText = (TextView) convertView.findViewById(R.id.menu_text);
            viewHolder.arrow = (ImageView) convertView.findViewById(R.id.menu_arrow);
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
            viewHolder.menuText.setText(themes.get(position).getName());
        }
        return convertView;
    }

    public void setSelectedMenu(int selectedMenu) {
        this.selectedMenu = selectedMenu;
        notifyDataSetChanged();
    }

    private static class MenuViewHolder {
        private ImageView leftImg;
        private TextView menuText;
        private ImageView arrow;
    }
}
