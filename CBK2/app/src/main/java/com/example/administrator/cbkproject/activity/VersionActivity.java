package com.example.administrator.cbkproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.cbkproject.R;

/**
 * Created by Administrator on 2016/6/23.
 */
public class VersionActivity extends Activity {
    private TextView versionName,versionCode;
    private String vName;
    private String vCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_layout);

        versionName = (TextView) findViewById(R.id.version_appname_tv);
        versionCode = (TextView) findViewById(R.id.version_appversion_tv);
        getAppVersionName(this);

        versionName.setText(R.string.app_name);
        versionCode.setText("当前版本为："+vCode+"");
    }

    private  void getAppVersionName(Context context) {
        try {
            // ---get the package info---
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            vCode = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
    }
}
