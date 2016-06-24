package com.example.administrator.cbkproject.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.administrator.httplib.BitmapRequest;
import com.example.administrator.httplib.Request;
import com.example.administrator.httplib.RequestQuee;

/**
 * Created by Administrator on 2016/6/13.
 * 网络请求。
 */
public class NetCache {
    private LocalCache localCache;
    private MemoryCache memoryCache;

    public NetCache(LocalCache localCache, MemoryCache memoryCache) {
        this.localCache = localCache;
        this.memoryCache = memoryCache;
    }





        protected void bitmapFromNet(final ImageView imageView, final String path) {
            final BitmapRequest request = new BitmapRequest(path, Request.Method.GET, new Request.CallBack<Bitmap>() {
                @Override
                public void onError(Exception e) {

                }

                @Override
                public void onResult(final Bitmap bitmap) {
                    if (bitmap != null) {
                        //判断imageView是否重用。--解决Imageview的错位问题。
                        if (imageView.getTag().equals(path)) {
                            //1：将图片设置给ImageView.
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                        //2:将图片保存到sk卡中
                        String filename = null;
                        filename = path.substring(path.lastIndexOf("/") + 1);


                        localCache.setBitmap(filename, bitmap);
                        //3:将图片保存到内存中
                        memoryCache.putImage(path, bitmap);
                    } else {
                        System.out.println("图片为空");
                    }
                }
            });
            RequestQuee requestQuee = RequestQuee.getInstance();
            // requestQuee.initDispatcher();
            requestQuee.addRequest(request);



        }




}
