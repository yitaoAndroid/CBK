package com.example.administrator.cbkproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.cbkproject.R;

/**
 * Created by Administrator on 2016/6/20.
 */
public class LogingActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loging_activity);
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                boolean isFirst = SharedPreferenceUtil.getBoolean(Constance.SHAREDPREFERENS_ISFIRSTLOGING,
                        Constance.PREFERENCE_MODEL, LogingActivity.this, Constance.ISFIRSTLOGING, true);
                if (!isFirst) {
                    intent = new Intent(LogingActivity.this, HomeActivity.class);
                    LogingActivity.this.startActivity(intent);
                    finish();
                    return;
                }
                intent = new Intent(LogingActivity.this, WelecomeActivity.class);
                LogingActivity.this.startActivity(intent);
                finish();
            }
        }, 3000);*/

        Intent intent = new Intent(LogingActivity.this, HomeActivity.class);
        LogingActivity.this.startActivity(intent);
        finish();

  /*      System.out.println("LogingActivity");
        Request request = new StringRequest("https://route.showapi.com/578-6?", Request.Method.POST, new StringRequest.CallBack<String>() {


            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResult(String result) {

            }
        });

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("showapi_appid", "=5286");
        map.put("showapi_timestamp", "=20160620231146");
        map.put("showapi_sign", "=81976183ce97607195a632b912ba8135");
        request.setPostParams(map);

        RequestQuee requestQuee = new RequestQuee();
        requestQuee.initDispatcher();
        requestQuee.addRequest(request);*/
    }
}
