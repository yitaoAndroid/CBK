package com.example.administrator.cbkproject.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.cbkproject.R;
import com.example.administrator.cbkproject.bean.Info;
import com.example.administrator.cbkproject.utils.Constance;
import com.example.administrator.cbkproject.utils.StringUtils;
import com.example.administrator.httplib.Request;
import com.example.administrator.httplib.RequestQuee;
import com.example.administrator.httplib.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/6/22.
 */


public class DetailActivity extends Activity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {
    private WebView webView;
    private Toolbar toolbar;
    private TextView title, keyTextView;
    private Info info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.detail_webview);
        webView.setWebViewClient(new WebViewClient());
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        title = (TextView) findViewById(R.id.detal_title);
        keyTextView = (TextView) findViewById(R.id.detal_key);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        toolbar.setNavigationIcon(R.mipmap.pre_);
        toolbar.inflateMenu(R.menu.detail_menu);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(this);

        if (url != null) {
            getMessage(url);

        }

    }

    private void getMessage(String url) {

        StringRequest request = new StringRequest(url, Request.Method.GET, new Request.CallBack<String>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResult(String result) {
                if (result == null) {
                    return;
                }
                try {
                    info = new Info();
                    JSONObject jsonObject = new JSONObject(result);
                    info.setTitle(jsonObject.optString("title"));
                    info.setTime(jsonObject.optLong("time"));
                    Log.i("mytime",info.getTime()+"");
                    info.setKeywords(jsonObject.optString("keywords"));
                    info.setUrl(jsonObject.optString("url"));
                    info.setId(jsonObject.optLong("id"));
                    info.setDescription(jsonObject.optString("description"));
                    info.setImage(jsonObject.optString("img"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(info);
                        if (info != null && !TextUtils.isEmpty(info.getUrl())) {
                            webView.loadUrl(info.getUrl());
                            title.setText(info.getTitle());
                            keyTextView.setText(info.getKeywords() + "  " + StringUtils.parseTime(info.getTime()));
                            insertToCollection(Constance.NOTREAD);
                        }
                    }
                });
            }
        });

        RequestQuee.getInstance().addRequest(request);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_detail_add:
                if (info == null) {
                    return false;
                }
                insertToCollection(Constance.HASREAD);
                break;

            case R.id.menu_detail_share:
                showShare(info.getUrl(),info.getTitle(),info.getDescription(),info.getImage());
                break;

        }
        return true;
    }



    private void insertToCollection(final int hasRead) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver resolver = getContentResolver();
                List<Integer> list = new ArrayList<Integer>();
                Cursor cursor = resolver.query(Uri.parse("content://com.cbk.pro"), null, null, null, null);
                if(cursor!=null) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        list.add(new Integer(id));
                    }
                }
                if (list.contains(new Integer((int) info.getId()))&&hasRead==Constance.NOTREAD) {
                    resolver = null;
                    cursor.close();
                    list = null;
                    return;
                }
                ContentValues values = new ContentValues();
                values.put("_id", (int) info.getId());
                values.put("title", info.getTitle());
                values.put("time", info.getTime());
                values.put("url", Constance.DETAIL_BASE_URL + info.getId());
                values.put("key", info.getDescription());
                values.put("imagepath", Constance.NEWSDETAIL_IMAGE_BASEPATH + info.getImage() + "_100x100");
                values.put("hasread", hasRead + "");
                if(hasRead==Constance.NOTREAD) {
                    resolver.insert(Uri.parse("content://com.cbk.pro/collection"), values);
                }else {
                    cursor = resolver.query(Uri.parse("content://com.cbk.pro"), new String[]{"hasread"}, "_id=?", new String[]{info.getId()+""}, null);
                    int hr = 0;
                    while(cursor.moveToNext()){
                        hr = Integer.parseInt(cursor.getString(cursor.getColumnIndex("hasread")));
                        break;
                    }
                    if(hr==Constance.HASREAD){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DetailActivity.this,"已经收藏了",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                       int result =  resolver.update(Uri.parse("content://com.cbk.pro/collection"), values,"_id=?",new String[]{""+info.getId()});
                        if(result>0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this,"无法收藏",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                resolver = null;
                cursor.close();
                list = null;
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        finish();
    }



    private void showShare(String url,String title,String desc,String imagePath) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("茶百科");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
       // oks.setImagePath(imagePath);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(desc);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

// 启动分享GUI
        oks.show(this);
    }


}
