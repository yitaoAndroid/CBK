package com.example.administrator.cbkproject.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.cbkproject.R;
import com.example.administrator.cbkproject.bean.CollectionBean;
import com.example.administrator.cbkproject.utils.BitmapCache;
import com.example.administrator.cbkproject.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class CollectionAdapter extends BaseAdapter {
    private List<CollectionBean> list;

    public CollectionAdapter() {
        list = new ArrayList<CollectionBean>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHelper helper = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.collection_item_layout, null);
            helper = new ViewHelper();
            helper.collectIv = (ImageView) convertView.findViewById(R.id.collection_item_iv);
            helper.timeTv = (TextView) convertView.findViewById(R.id.collection_item_tv_time);
            helper.titleTv = (TextView) convertView.findViewById(R.id.collection_item_tv_title);
            helper.keyTv = (TextView) convertView.findViewById(R.id.collection_item_tv_key);
            convertView.setTag(helper);
        }

        helper = (ViewHelper) convertView.getTag();
        CollectionBean collectionBean = list.get(position);
        helper.keyTv.setText(collectionBean.getKeywords());
        helper.timeTv.setText(StringUtils.parseTime(collectionBean.getTime()));
        helper.titleTv.setText(collectionBean.getTitle());
        helper.collectIv.setImageResource(R.mipmap.logo);
        helper.collectIv.setTag(collectionBean.getImage());
        BitmapCache.getInstance().setImageViewFromCache(collectionBean.getImage(), helper.collectIv);
        collectionBean = null;
        return convertView;
    }


    class ViewHelper {
        ImageView collectIv;
        TextView titleTv;
        TextView keyTv;
        TextView timeTv;
    }

    public void clear(){
        list.clear();
    }

    public void  addAll(List<CollectionBean> collectionBeanList){
        list.addAll(collectionBeanList);
    }

    public void  remove(CollectionBean bean){
        list.remove(bean);
    }

}
