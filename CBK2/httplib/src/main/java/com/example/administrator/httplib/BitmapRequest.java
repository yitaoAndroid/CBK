package com.example.administrator.httplib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Administrator on 2016/6/21.
 */
public class BitmapRequest extends Request<Bitmap> {
    private CallBack<Bitmap> callBack;
    private String url;

    public BitmapRequest(String url, Method method, CallBack callBack) {
        super(url, method, callBack);
        this.callBack = callBack;
        this.url = url;
    }

    @Override
    public void onGetResult(byte[] result) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
        if (callBack != null) {
            callBack.onResult(bitmap);
            Log.v("bitmap",bitmap+"");
        }
    }

    public String getUrl() {
        return url;
    }
}



    /*public interface OnGetIMageListener{
        public void onGetImage(ImageView imageView,String url);
    }

    private OnGetIMageListener listener;

    public void  setListener( OnGetIMageListener listener){
        this.listener = listener;
    }*/

