package com.example.administrator.cbkproject.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.cbkproject.R;
import com.example.administrator.cbkproject.bean.Info;
import com.example.administrator.cbkproject.utils.BitmapCache;
import com.example.administrator.cbkproject.utils.Constance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/21.
 */
public class ContentListViewAdapter extends BaseAdapter {
    private List<Info> infos = new ArrayList<>();
    private ViewHelper viewHelper;

    @Override
    public int getCount() {
        return infos == null ? 0 : infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHelper = null;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.contentfrag_list_item, null);
            viewHelper = new ViewHelper();
            viewHelper.tvDesc = (TextView) convertView.findViewById(R.id.content_desc);
            viewHelper.tvTime = (TextView) convertView.findViewById(R.id.content_time);
            viewHelper.reCount = (TextView) convertView.findViewById(R.id.content_rc);
            viewHelper.contentIv = (ImageView) convertView.findViewById(R.id.contentfrag_iv);
            convertView.setTag(viewHelper);
        }
        viewHelper = (ViewHelper) convertView.getTag();
        Info info = infos.get(position);
        viewHelper.tvDesc.setText(info.getDescription());
        viewHelper.reCount.setText(info.getRcount());
        viewHelper.tvTime.setText(getTimeString(info.getTime()));
        viewHelper.contentIv.setImageResource(R.mipmap.firstdefault);

        final String url = info.getImage().contains("http://") ? info.getImage(): Constance.NEWSDETAIL_IMAGE_BASEPATH+info.getImage()+"_100x100";
        viewHelper.contentIv.setTag(url);
        BitmapCache.getInstance().setImageViewFromCache(url,viewHelper.contentIv);

        return convertView;
    }

    class ViewHelper {

        ImageView contentIv;
        TextView tvTime;
        TextView tvDesc;
        TextView reCount;

    }

    private String getTimeString(long sec) {
        String time = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = format.format(sec);
        return time;
    }

    public void addAll(List<Info> list) {
        if(list!=null)
        this.infos.addAll(list);
    }

    public void clear() {
        infos.clear();
    }

    public List<Info> getAll(){
        return this.infos;
    }
}
