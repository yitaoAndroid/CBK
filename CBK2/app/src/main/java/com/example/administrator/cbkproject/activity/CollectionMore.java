package com.example.administrator.cbkproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.cbkproject.R;

/**
 * Created by Administrator on 2016/6/22.
 */
public class CollectionMore extends Activity implements View.OnClickListener {
    private TextView collection, history, info, fankui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_more);

        init();
    }

    private void init() {

        collection = (TextView) findViewById(R.id.setting_collection);
        history = (TextView) findViewById(R.id.setting_history);
        info = (TextView) findViewById(R.id.setting_info);
        fankui = (TextView) findViewById(R.id.setting_fankui);


        collection.setOnClickListener(this);
        history.setOnClickListener(this);
        info.setOnClickListener(this);
        fankui.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.setting_collection:
                intent = new Intent(this,CollectionActivity.class);
                intent.putExtra("type","collection");
                startActivity(intent);
                break;

            case R.id.setting_history:
                intent = new Intent(this,CollectionActivity.class);
                intent.putExtra("type","history");
                startActivity(intent);
                break;

            case R.id.setting_info:
                intent = new Intent(this,VersionActivity.class);
                startActivity(intent);
                break;

            case R.id.setting_fankui:
                intent = new Intent(this,FanKuiActivity.class);
                startActivity(intent);
                break;
        }
    }
}
